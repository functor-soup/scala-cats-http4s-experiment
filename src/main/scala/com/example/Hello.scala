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

object Hello extends StreamApp {

  val httpClient = PooledHttp1Client()

  val service = HttpService {
    case GET -> Root => httpClient.expect[String](Uri.unsafeFromString(Constants.mainUrl))
                                  .flatMap(Ok(_))
   }

  override def stream(args: List[String]): Stream[Task, Nothing] = {
  BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .serve
  }
}
