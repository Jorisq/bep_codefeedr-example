ThisBuild / resolvers ++= Seq(
  "Apache Development Snapshot Repository" at "https://repository.apache.org/content/repositories/snapshots/",
  Resolver.mavenLocal,
  "Artifactory" at "http://codefeedr.joskuijpers.nl:8081/artifactory/sbt-dev-local/"
)

name := "bep_codefeedr-example"

version := "0.1-SNAPSHOT"

organization := "org.example"

ThisBuild / scalaVersion := "2.11.11"

val flinkVersion = "1.4.2"

val flinkDependencies = Seq(
  "org.apache.flink" %% "flink-scala" % flinkVersion % Compile,
  "org.apache.flink" %% "flink-streaming-scala" % flinkVersion % Compile)

val codefeedrDependencies = Seq(
  "org.codefeedr" %% "codefeedr-core" % "0.1-SNAPSHOT"
)

lazy val root = (project in file(".")).
  settings(
    libraryDependencies ++= flinkDependencies ++ codefeedrDependencies
  )

assembly / mainClass := Some("org.example.Main")

// make run command include the provided dependencies
Compile / run  := Defaults.runTask(Compile / fullClasspath,
  Compile / run / mainClass,
  Compile / run / runner
).evaluated

// stays inside the sbt console when we press "ctrl-c" while a Flink programme executes with "run" or "runMain"
Compile / run / fork := true
Global / cancelable := true

// exclude Scala library from assembly
assembly / assemblyOption  := (assembly / assemblyOption).value.copy(includeScala = false)