import uk.gov.hmrc.DefaultBuildSettings._

name := "microservice-async"

lazy val library = (project in file("."))
    .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning)
    .settings(scalaSettings: _*)
    .settings(defaultSettings(): _*)
    .settings(
      libraryDependencies ++= AppDependencies(),
      scalaVersion := "2.11.8",
      crossScalaVersions := Seq("2.11.8"),
      resolvers := Seq(
        Resolver.bintrayRepo("hmrc", "releases"),
        "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
      )
    )
