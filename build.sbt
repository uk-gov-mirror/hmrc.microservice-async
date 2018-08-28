import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.SbtArtifactory

name := "microservice-async"

lazy val library = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
    .settings(scalaSettings: _*)
    .settings(defaultSettings(): _*)
    .settings(
      majorVersion := 2,
      libraryDependencies ++= AppDependencies(),
      scalaVersion := "2.11.8",
      crossScalaVersions := Seq("2.11.8"),
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      )
    )
