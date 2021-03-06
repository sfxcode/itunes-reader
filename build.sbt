val username = "sfxcode"
val repo     = "simple-mongo"

scalaVersion := "2.13.2"

crossScalaVersions := Seq("2.13.2", "2.12.11")

name := "itunes-reader"
organization := "com.sfxcode.music"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

lazy val root = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion)
  )

buildInfoOptions += BuildInfoOption.BuildTime

buildInfoPackage := "com.sfxcode.music.itunes"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.9.4" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.6" % Test

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.3.0"

libraryDependencies += "joda-time" % "joda-time" % "2.10.6"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"

// publish

releaseCrossBuild := true

bintrayReleaseOnPublish in ThisBuild := true

publishMavenStyle := true

homepage := Some(url("https://github.com/sfxcode/itunes-reader"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/sfxcode/itunes-reader"),
    "scm:https://github.com/sfxcode/itunes-reader.git"
  )
)

developers := List(
  Developer(
    id = "sfxcode",
    name = "Tom Lamers",
    email = "tom@sfxcode.com",
    url = url("https://github.com/sfxcode")
  )
)
