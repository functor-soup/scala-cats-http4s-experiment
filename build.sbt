name := """blockchain-api"""

version := "1.0"

scalaVersion := "2.12.1"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

// Uncomment to use Akka
libraryDependencies ++= Seq("org.scalaz" %% "scalaz-core" % "7.2.12",
                            "org.http4s" %% "http4s-core" % "0.15.11a",
                            "org.http4s" %% "http4s-blaze-server" % "0.15.11a",
                            "org.http4s" %% "http4s-blaze-client" % "0.15.11a",
                            "org.http4s" %% "http4s-dsl" % "0.15.11a")


