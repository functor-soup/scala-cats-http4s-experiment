package com.example

import org.http4s._
import org.http4s.dsl._
import org.http4s.server.blaze._
import org.http4s.server.syntax._
import fs2.{Stream, Task}
import org.http4s.util.StreamApp

object Hello extends StreamApp {

   val service = HttpService {
    case GET -> Root => Ok(s"Hello") 
   }

  override def stream(args: List[String]): Stream[Task, Nothing] = {
  BlazeBuilder
      .bindHttp(8080, "localhost")
      .mountService(service, "/")
      .serve
  }
}
