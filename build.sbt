ThisBuild / scalaVersion := "3.3.0-RC3"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

lazy val zioVersion = "2.0.10"

lazy val root = (project in file("."))
  .settings(
    name := "zio-stream-bug",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % zioVersion withSources(),
      "dev.zio" %% "zio-streams" % zioVersion withSources(),
      "co.fs2" %% "fs2-core" % "3.6.1",
      "co.fs2" %% "fs2-reactive-streams" % "3.6.1",
      "co.fs2" %% "fs2-io" % "3.6.1",
      "dev.zio" %% "zio-interop-reactivestreams" % "2.0.1" withSources(),
      "dev.zio" %% "zio-interop-cats" % "23.0.0.2" withSources(),
      "dev.zio" %% "zio-http" % "0.0.5" withSources(),
      "dev.zio" %% "zio-test" % zioVersion % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
