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
libraryDependencies += "com.typesafe" % "config" % "1.4.3"

assemblyMergeStrategy in assembly := {
  case "application.conf"                            => MergeStrategy.first
  //case "logback.xml"                                 => MergeStrategy.first
  case "log4j.properties"                            => MergeStrategy.discard
  case m if m.toLowerCase.endsWith("manifest.mf")    => MergeStrategy.discard
  case "module-info.class"                           => MergeStrategy.discard
  //case PathList("META-INF", xs @ *)                  => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}