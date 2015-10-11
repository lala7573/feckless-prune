name := "umbrella"

version := "1.0"

lazy val `umbrella` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws,
  "com.ganyo" % "gcm-server" % "1.0.2",
  "com.googlecode.json-simple" % "json-simple" % "1.1"
)
//  "com.typesafe.slick" %% "slick" % "3.1.0",
//  "com.typesafe.play" % "play-slick_2.11" % "1.1.0",
//  "mysql" % "mysql-connector-java" % "5.1.36"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )