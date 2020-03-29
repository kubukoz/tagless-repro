val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      compilerPlugin(
        "org.typelevel" % "kind-projector" % "0.11.0" cross CrossVersion.full
      ),
      "org.typelevel" %% "cats-tagless-macros" % "0.11"
    ),
    scalacOptions ++= Seq("-Ymacro-annotations", "-language:higherKinds"),
    scalaVersion := "2.13.1"
  )
