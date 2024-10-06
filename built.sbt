name         := "lorem-ipsum"
organization := "fr.janalyse"
description  := "Simple lorem ipsum text generator"

licenses += "NON-AI-APACHE2" -> url(s"https://github.com/non-ai-licenses/non-ai-licenses/blob/main/NON-AI-APACHE2")

scalaVersion       := "3.5.1"
crossScalaVersions := Seq("2.12.20", "2.13.15", "3.5.1")

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19" % Test

Test / testOptions += {
  val rel = scalaVersion.value.split("[.]").take(2).mkString(".")
  Tests.Argument(
    "-oDF", // -oW to remove colors
    "-u",
    s"target/junitresults/scala-$rel/"
  )
}

homepage   := Some(url("https://github.com/dacr/lorem-ipsum"))
scmInfo    := Some(ScmInfo(url(s"https://github.com/dacr/lorem-ipsum.git"), s"git@github.com:dacr/lorem-ipsum.git"))
developers := List(
  Developer(
    id = "dacr",
    name = "David Crosson",
    email = "crosson.david@gmail.com",
    url = url("https://github.com/dacr")
  )
)
