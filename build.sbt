ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "XmlClient"
  )
val akkaV = "2.5.19" //must be sync with lcn-library-akka
val httpV = "10.1.6"

val akka: Seq[ModuleID] =
  Seq(
    "com.typesafe.akka" %% "akka-actor"           % akkaV,
    "com.typesafe.akka" %% "akka-slf4j"           % akkaV,
    "com.typesafe.akka" %% "akka-stream"          % akkaV,
    "com.typesafe.akka" %% "akka-http-core"       % httpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % httpV,
    "com.typesafe.akka" %% "akka-http-xml"        % httpV
  )
libraryDependencies ++= akka