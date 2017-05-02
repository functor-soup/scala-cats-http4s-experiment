name := """blockchain-api"""

version := "1.0"

scalaVersion := "2.12.1"

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"

// Uncomment to use Akka
libraryDependencies ++= Seq("org.typelevel" %% "cats" % "0.9.0",
                            "org.http4s" %% "http4s-core" % "0.17.0-M2")

val circeVersion = "0.7.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

