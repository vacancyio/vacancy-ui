name := """vacancy-ui"""

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

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
