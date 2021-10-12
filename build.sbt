ThisBuild / scalaVersion     := "3.0.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "bank-account-zio",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.0-M3",
      "dev.zio" %% "zio-test" % "2.0.0-M3" % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
