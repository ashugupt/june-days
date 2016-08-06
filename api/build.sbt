cancelable in Global := true

libraryDependencies ++= Vector(
  Dependencies.akkaHttpCore,
  Dependencies.akkaHttpExperimental,
  Dependencies.circeJava8,
  Dependencies.akkaHttpCirce,
  Dependencies.akkaHttpTestkit
)