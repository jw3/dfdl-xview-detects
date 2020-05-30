lazy val `dfdl-xview-detects` =
  project
    .in(file("."))
    .aggregate(
      `detects-schema`,
      `detects-service`
    )
    .settings(commonSettings: _*)
    .enablePlugins(GitVersioning)

lazy val `detects-schema` =
  project
    .in(file("schema"))
    .settings(commonSettings: _*)
    .settings(
      libraryDependencies ++= commonLibraries,
      testOptions += Tests.Argument(TestFrameworks.JUnit, "-v"),
      crossPaths := false
    )
    .enablePlugins(GitVersioning)

lazy val `detects-service` =
  project
    .in(file("service"))
    .dependsOn(`detects-schema`)
    .settings(commonSettings: _*)
    .settings(
      version in Docker := "latest",
      dockerExposedPorts := Seq(9000),
      dockerRepository := Some("ctcoss"),
      libraryDependencies ++= commonLibraries
    )
    .enablePlugins(GitVersioning, JavaServerAppPackaging)

/**
  *
  * Commons
  *
  */
lazy val commonSettings = Seq(
  organization := "com.ctc",
  scalaVersion := "2.12.11",
  git.useGitDescribe := true,
  scalacOptions ++= Seq(
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked",
    "-deprecation",
    "-language:postfixOps",
    "-language:implicitConversions",
    "-Ywarn-unused-import",
    "-Xfatal-warnings",
    "-Xlint:_"
  )
)

lazy val commonLibraries = {
  Seq(
    "org.apache.daffodil" %% "daffodil-sapi" % "latest.integration",
    "com.typesafe.akka" %% "akka-actor-typed" % "2.6.5",
    "com.typesafe.akka" %% "akka-stream" % "2.6.5",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.12",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
    "org.apache.daffodil" %% "daffodil-tdml-processor" % "2.6.0" % Test,
    "com.novocode" % "junit-interface" % "0.11" % Test
  )
}
