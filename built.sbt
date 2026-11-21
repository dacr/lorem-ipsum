name         := "lorem-ipsum"
organization := "fr.janalyse"
description  := "Simple lorem ipsum text generator"

licenses := Seq("Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"))

scalaVersion       := "3.3.9"
crossScalaVersions := Seq("2.12.21", "2.13.18", "3.3.9")

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.20" % Test

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
