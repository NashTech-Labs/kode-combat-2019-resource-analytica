import Versions.JDKVersion
import sbt.Test

lazy val PuneAnalytica = Project("pune-analytica", file(".")).settings(
  name := "pune-analytica",
  description := "Rest API",
  version := "0.1",
  coverageMinimum := 75,
  coverageFailOnMinimum := false,
  Compile / mainClass := Some("pune.analytica.HttpServerApp"),
  scalaVersion := Versions.ScalaVersion,
  scalacOptions ++= Seq(
    s"-target:jvm-$JDKVersion",
    "-encoding",
    "UTF-8",
    "-Xfatal-warnings",
    "-Xlint:-unused,_",
    "-Ywarn-dead-code",
    "-Ywarn-unused:imports", // Warn if an import selector is not referenced
    "-Ywarn-unused:locals", // Warn if a local definition is unused
    "-Ywarn-unused:patvars", // Warn if a variable bound in a pattern is unused
    "-Ywarn-unused:privates", // Warn if a private member is unused
    "-deprecation"
  ),
  Test / scalacOptions -= "-Ywarn-dead-code", // For scala-mockito. See https://github.com/mockito/mockito-scala#dead-code-warning
  javacOptions ++= Seq(
    "-source",
    JDKVersion,
    "-target",
    JDKVersion,
    "-encoding",
    "UTF-8"
  ),
  libraryDependencies ++= Dependencies.Compile.All ++ Dependencies.Test.All,
  Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD")
)
