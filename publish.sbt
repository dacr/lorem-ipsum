pomIncludeRepository := { _ => false }

licenses += "Apache 2" -> url(s"http://www.apache.org/licenses/LICENSE-2.0.txt")
releasePublishArtifactsAction := PgpKeys.publishSigned.value
releaseCrossBuild := false
publishMavenStyle := true
publishArtifact in Test := false
publishTo := Some(if (isSnapshot.value) Opts.resolver.sonatypeSnapshots else Opts.resolver.sonatypeStaging)

scmInfo := Some(ScmInfo(url(s"https://github.com/dacr/lorem-ipsum"), s"git@github.com:dacr/lorem-ipsum.git"))

PgpKeys.useGpg in Global := true      // workaround with pgp and sbt 1.2.x
pgpSecretRing := pgpPublicRing.value  // workaround with pgp and sbt 1.2.x

pomExtra in Global := {
  <developers>
    <developer>
      <id>dacr</id>
      <name>David Crosson</name>
      <url>https://github.com/dacr</url>
    </developer>
  </developers>
}

