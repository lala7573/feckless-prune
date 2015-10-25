name := "umbrella"

version := "1.0-SNAPSHOT"

lazy val `umbrella` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws,
  "com.ganyo" % "gcm-server" % "1.0.2",
  "com.googlecode.json-simple" % "json-simple" % "1.1",
  "org.json" % "json" % "20141113",
  "com.typesafe.play" % "play-slick_2.11" % "0.8.0",
  "com.typesafe.slick" % "slick_2.11" % "2.1.0",
  "mysql" % "mysql-connector-java" % "5.1.36"
)


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )