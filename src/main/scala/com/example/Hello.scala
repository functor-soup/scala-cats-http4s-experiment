package com.example

import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze._
import org.http4s.server.syntax._
import org.http4s.client.blaze._
import org.http4s.client.Client
import org.http4s.server.{Server, ServerApp}
import Logger.Printer._
import scalaz._
import Scalaz._
import scalaz.concurrent.Task
import constants.Constants
import org.http4s.argonaut._
import _root_.argonaut._
import ArgonautShapeless._

object Hello extends ServerApp {

  type Logger[A] = WriterT[Task,String,A]

  case class Rates(from: String, value: Float)

  val httpClient = PooledHttp1Client()

  implicit val FooCodec = CodecJson.derive[Rates]

  def operationPlusLogging(url:String): Logger[String] = 
        WriterT
         .put(httpClient.expect[String](Uri.unsafeFromString(url)))(info("Client issued a Get request to / " )) 
         .:++>> (b => info(s"Server served $b"))

  def operationPostPlusLogging(js:Rates): Logger[String] = 
        WriterT
         .put(httpClient.expect[String](Uri.unsafeFromString(Constants.rateCall(js.from, js.value))))(info(s"Client issued a POST request to / with body ${js.toString}"))
         .:++>> (b => info(s"Server served $b"))
         .:++>>(_ => "Luke says hello!")


  def opOutput(url: String):String = {
   val (log, reg) = (operationPlusLogging(url)).run.run
   println(log)
   return reg
  }

  def postOutput(req: Rates):String = {
   val (log, reg) = (operationPostPlusLogging(req)).run.run
   println(log)
   return reg
  }

  val service = HttpService {
    case GET -> Root => Ok(opOutput(Constants.mainUrl))
    case req @ POST -> Root => jsonOf[Rates].decode(req, strict = true).run
      .flatMap(x => x.fold(y => Ok("Something fucked up"), y => Ok(postOutput(y))))
   }

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .start
  }
}
