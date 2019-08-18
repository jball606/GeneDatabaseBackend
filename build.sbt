import play.sbt.PlayScala
import play.sbt.routes.RoutesKeys.routesImport
import sbt.Keys.resolvers

name := """invitae"""
organization := "com.example"
version := "1.0-SNAPSHOT"


lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

PlayKeys.fileWatchService := play.dev.filewatch.FileWatchService.polling(2000)
updateOptions := updateOptions.value.withCachedResolution(true)


javaOptions in Universal ++= Seq(
    "-J-Xmx8G",
    "-J-Xms4G",
    "-J-Xss6G",

    "-J-XX:+UseParallelGC",
    "-J-XX:+HeapDumpOnOutOfMemoryError",
    "-Dcom.sun.management.jmxremote",
    "-Dcom.sun.management.jmxremote.port=1099",
    "-Dcom.sun.management.jmxremote.rmi.port=1099",
    "-Dcom.sun.management.jmxremote.local.only=false",
    "-Dcom.sun.management.jmxremote.ssl=false",
    "-Dcom.sun.management.jmxremote.authenticate=false"
)
libraryDependencies ++= Seq(
    "org.scalatestplus.play"                %% "scalatestplus-play"                 % "4.0.3" % Test,
    "ai.x"                                  %% "play-json-extensions"               % "0.10.0",
    ehcache,
    guice
)
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
