package com.example

import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze._
import org.http4s.server.syntax._
import fs2.{Stream, Task}
import org.http4s.util.StreamApp
import org.http4s.client.blaze._
import cats.implicits._
import constants.Constants
import cats.syntax.applicative._
import cats.data.Writer
import org.http4s.client.Client

object Hello extends StreamApp {

  type Logger[A] = Writer[String,A]

  val httpClient = PooledHttp1Client()

  def kik(url: String):Task[String] = {
    val egg = "[LOG] client issued a get request\n".tell
      .map(_ => httpClient.expect[String](Uri.unsafeFromString(url)))
      .flatMap(x => for { _ <- s"[LOG] response recieved a $x \n".tell} yield x)

   val (log, reg) = egg.run
   println(log)
   return reg
  }

  val service = HttpService {
    case GET -> Root => kik(Constants.mainUrl).flatMap(Ok(_))
   }

  override def stream(args: List[String]): Stream[Task, Nothing] = {
    BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .serve
  }
}
