maintainer     := "Ashu Gupta <ashu.a.gupta@gmail.com>"
packageSummary := "An demo audio streaming library using concepts of Functional Programming."

lazy val commonSettings = Seq(
  organization := "com.ashugupt.fp.june",
  version      := "0.0.1-SNAPSHOT",
  scalaVersion := Version.Scala
)

lazy val root = project
  .copy(id = "june")
  .in(file("."))
  .enablePlugins(JavaAppPackaging, BuildInfoPlugin, GitVersioning)
  .settings(commonSettings: _*)
  .settings(compilationSettings: _*)
  .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)

lazy val core = project
  .copy(id = "june-core")
  .in(file("core"))
  .enablePlugins(GitVersioning)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibs)
  .settings(compilationSettings: _*)
  .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)

lazy val api = project
  .copy(id = "june-api")
  .in(file("api"))
  .enablePlugins(GitVersioning, BuildInfoPlugin)
  .settings(commonSettings: _*)
  .settings(libraryDependencies ++= commonLibs)
  .settings(compilationSettings: _*)
  .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)
  .settings(
    buildInfoKeys    := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, buildInfoBuildNumber),
    buildInfoOptions := Seq[BuildInfoOption](BuildInfoOption.BuildTime),
    buildInfoPackage := "health"
  )
  .dependsOn(core)

lazy val commonLibs = Vector(
  Dependencies.slf4jApi,
  Dependencies.logbackApi,

  Dependencies.catsCore,
  Dependencies.catsFree,
  Dependencies.catsKernel,
  Dependencies.catsMacros,
  Dependencies.catsLaws,

  Dependencies.akkaActor,
  Dependencies.akkaSlf4j,
  Dependencies.akkaStream,
  Dependencies.akkaPersistence,
  Dependencies.akkaPersistenceQuery,
  Dependencies.akkaPersistenceTck,
  Dependencies.akkaTyped,

  Dependencies.junit,
  Dependencies.scalaCheck,
  Dependencies.scalaTest,
  Dependencies.akkaTestkit,
  Dependencies.akkaMultinodeTestkit
)

lazy val compilationSettings = Vector(
  // compile settings
  compileOrder := CompileOrder.Mixed,

  // javac settings
  javacOptions ++= Vector(
    "-g:none",
    "-source", "1.8",
    "-target", "1.8"
  ),

  // scalac settings
  scalacOptions ++= Vector(
    "-unchecked",
    "-feature",
    "-deprecation",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials",
    "-language:postfixOps",
    "-target:jvm-1.8",
    "-encoding", "UTF-8",
    "-Xfuture",
    "-Xfatal-warnings",
    "-Xlint",
    "-Ywarn-value-discard",
    "-Ywarn-dead-code",
    "-Ywarn-adapted-args"
  )
)

