
scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.12.6", "2.11.11")

name := "itunes-reader"
organization := "com.sfxcode.music"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.sfxcode.music.itunes"
  )

buildInfoOptions += BuildInfoOption.BuildTime

buildInfoPackage := "com.sfxcode.music.itunes"


libraryDependencies += "org.specs2" %% "specs2-core" % "4.3.2" % Test

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3" % Test

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.5" % Test


libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.1.0"

libraryDependencies += "joda-time" % "joda-time" % "2.10"

libraryDependencies += "com.typesafe" % "config" % "1.3.3"


libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayReleaseOnPublish in ThisBuild := true


