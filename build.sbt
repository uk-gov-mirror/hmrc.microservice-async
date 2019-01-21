import uk.gov.hmrc.DefaultBuildSettings._
import uk.gov.hmrc.SbtArtifactory

name := "microservice-async"

val scalaV = "2.11.12"

lazy val library = (project in file("."))
  .enablePlugins(SbtAutoBuildPlugin, SbtGitVersioning, SbtArtifactory)
  .settings(scalaSettings: _*)
  .settings(defaultSettings(): _*)
  .settings(PlayCrossCompilation.playCrossCompilationSettings)
  .settings(
    majorVersion := 2,
    libraryDependencies ++= AppDependencies(),
    makePublicallyAvailableOnBintray := true,
    scalaVersion := scalaV,
    crossScalaVersions := Seq(scalaV),
    resolvers := Seq(
      Resolver.bintrayRepo("hmrc", "releases"),
      "typesafe-releases" at "http://repo.typesafe.com/typesafe/releases/"
    )
  )
