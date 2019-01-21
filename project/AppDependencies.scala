import sbt._

object AppDependencies {
  lazy val scope: String        = "test"

  private val play25Version = "2.5.19"
  private val play26Version = "2.6.21"

  val compile: Seq[ModuleID] = PlayCrossCompilation.dependencies(
    shared = Seq(
      "uk.gov.hmrc" %% "crypto" % "5.1.0",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.mockito" % "mockito-all" % "1.10.19" % "test"
    ),

    play25 = Seq(
      "uk.gov.hmrc" %% "play-config" % "7.2.0",
      "uk.gov.hmrc" %% "play-filters" % "5.18.0",
      "uk.gov.hmrc" %% "play-async" %"2.4.0-play-25",
      "uk.gov.hmrc" %% "hmrctest" % "3.4.0-play-25",
      "com.typesafe.play" %% "play-test" % play25Version % Test,
      "uk.gov.hmrc" %% "simple-reactivemongo" % "7.7.0-play-25",
      "uk.gov.hmrc" %% "reactivemongo-test" % "4.4.0-play-25" % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % Test
  ),

    play26 = Seq(
      "uk.gov.hmrc" %% "bootstrap-play-26" % "0.35.0",
      "uk.gov.hmrc" %% "play-async" % "2.4.0-play-26",
      "com.typesafe.play" %% "play-json" % "2.6.13",
      "uk.gov.hmrc" %% "hmrctest" % "3.4.0-play-26" % Test,
      "com.typesafe.play" %% "play-test" % play26Version % Test,
      "uk.gov.hmrc" %% "bootstrap-play-26" % "0.35.0",
      "uk.gov.hmrc" %% "simple-reactivemongo" % "7.7.0-play-26",
      "uk.gov.hmrc" %% "reactivemongo-test" % "4.4.0-play-26" % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % Test
    )
  )
  def apply(): Seq[ModuleID] = compile
}


