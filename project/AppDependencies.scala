import sbt._

object AppDependencies {

  import play.core.PlayVersion

  val compile = Seq(
    "uk.gov.hmrc" %% "play-async" % "2.3.0",
    "uk.gov.hmrc" %% "crypto" % "4.5.0",
    "uk.gov.hmrc" %% "play-filters" % "5.18.0",
    "uk.gov.hmrc" %% "play-config" % "7.2.0",
    
    "uk.gov.hmrc" %% "play-reactivemongo" % "6.2.0"
  )

  trait TestDependencies {
    lazy val scope: String        = "test"
    lazy val test : Seq[ModuleID] = ???
  }

  object Test {

    def apply() = new TestDependencies {
      override lazy val test = Seq(
        "org.scalatest" %% "scalatest" % "3.0.5" % scope,
        "uk.gov.hmrc" %% "hmrctest" % "3.3.0" % scope,
        "uk.gov.hmrc" %% "reactivemongo-test" % "3.1.0" % scope,
        "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
        "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.1" % scope,
        "org.mockito" % "mockito-all" % "1.10.19" % scope
      )
    }.test
  }
  def apply() = compile ++ Test()
}


