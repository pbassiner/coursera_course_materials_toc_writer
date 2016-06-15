mainClass := Some("com.pbassiner.fstree2md.Main")
fork in run := true

name := "fstree2md"
organization := "com.pbassiner"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.8"
scalacOptions ++= Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")
wartremoverWarnings ++= Warts.allBut(Wart.Throw)

libraryDependencies ++= {
  val scalaTestV       = "3.0.0-M15"
  val scalaMockV       = "3.2.2"
  Seq(
    "org.scalatest"          %% "scalatest"                            % scalaTestV       % "test",
    "org.scalamock"          %% "scalamock-scalatest-support"          % scalaMockV       % "test",
    "com.typesafe"           %  "config"                               % "1.3.0",
    "com.lihaoyi" %% "ammonite-ops" % "0.6.2"
  )
}

dependencyOverrides ++= {
  Set(
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scala-lang" % "scala-library" % scalaVersion.value
  )
}

scalariformSettings

