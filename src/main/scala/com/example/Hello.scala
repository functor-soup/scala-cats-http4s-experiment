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

object Hello extends ServerApp {

  type Logger[A] = WriterT[Task,String,A]

  val httpClient = PooledHttp1Client()


  def kik(url: String):String = {
    val egg: Logger[String] = for {
      _ <- WriterT.put(Task())(info("Client issued a Get request to / " ))
      b <- WriterT.put(httpClient.expect[String](Uri.unsafeFromString(url)))("")
      _ <- WriterT.put(Task(b))(info(s"Server served $b"))
    } yield b

   val (log, reg) = egg.run.run
   println(log)
   println(reg)
   return reg
  }

  val service = HttpService {
    case GET -> Root => Ok(kik(Constants.mainUrl))
   }

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .start
  }
}
