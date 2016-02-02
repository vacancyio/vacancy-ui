organization := "io.forward"

name := """vacancy"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  filters,
  evolutions,
  "org.scalaz"        %% "scalaz-core" % "7.1.4",
  "com.typesafe.play" %% "anorm"       % "2.4.0",
  "org.mindrot"        % "jbcrypt"     % "0.3m",
  "joda-time"          % "joda-time"   % "2.7",
  "org.postgresql"     % "postgresql"  % "9.4-1204-jdbc42",
  specs2 % Test
)

maintainer in Docker := "Owain Lewis <owain@owainlewis.com>"

dockerExposedPorts in Docker := Seq(9000, 9443)

dockerBaseImage := "java:8"

dockerEntrypoint := Seq("bin/vacacny", "-Dconfig.resource=application.prod.conf", "-DapplyEvolutions.default=true")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

routesGenerator := InjectedRoutesGenerator

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
