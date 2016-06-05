maintainer     := "Ashu Gupta <ashu.a.gupta@gmail.com>"
packageSummary := "An demo audio streaming library using concepts of Functional Programming."

lazy val commonSettings = Seq(
  organization := "com.ashugupt.fp.june",
  scalaVersion := Version.Scala,

  buildInfoKeys    := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion, buildInfoBuildNumber),
  buildInfoOptions := Seq[BuildInfoOption](BuildInfoOption.BuildTime),
  buildInfoPackage := "health"
)

lazy val root = project
  .copy(id = "june")
  .in(file("."))
  .enablePlugins(JavaAppPackaging, BuildInfoPlugin, GitVersioning, GitBranchPrompt)
  .settings(commonSettings: _*)
  .settings(gitSettings: _*)
  .settings(compilationSettings: _*)
  .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)

lazy val core = project
  .copy(id = "june-core")
  .in(file("core"))
  .enablePlugins(GitVersioning, GitBranchPrompt)
  .settings(commonSettings: _*)
  .settings(gitSettings: _*)
  .settings(compilationSettings: _*)
  .settings(libraryDependencies ++= commonLibs)
  .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)

lazy val api = project
  .copy(id = "june-api")
  .in(file("api"))
  .enablePlugins(GitVersioning, BuildInfoPlugin, GitBranchPrompt)
  .settings(commonSettings: _*)
  .settings(gitSettings: _*)
  .settings(compilationSettings: _*)
  .settings(libraryDependencies ++= commonLibs)
  .settings(net.virtualvoid.sbt.graph.Plugin.graphSettings)
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

  Dependencies.circeGeneric,
  Dependencies.circeCore,
  Dependencies.circeJawn,
  Dependencies.circeParser,
  Dependencies.circeOptics,

  Dependencies.junit,
  Dependencies.scalaCheck,
  Dependencies.scalaTest,
  Dependencies.akkaTestkit,
  Dependencies.akkaMultinodeTestkit
)

val VersionRegex = "v([0-9]+.[0-9]+.[0-9]+)-?(.*)?".r

lazy val gitSettings = Vector(
  git.baseVersion := "0.0.0",
  git.useGitDescribe := true,

  git.gitTagToVersionNumber := {
    case VersionRegex(v,"") => Some(v)
    case VersionRegex(v,"SNAPSHOT") => Some(s"$v-SNAPSHOT")
    case VersionRegex(v,s) => Some(s"$v-$s-SNAPSHOT")
    case _ => None
  }
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
    "-Ywarn-dead-code",
    "-Ywarn-adapted-args"
  )
)

