cancelable in Global := true

libraryDependencies ++= Vector(
  Dependencies.akkaHttpCore,
  Dependencies.akkaHttpExperimental,
  Dependencies.akkaHttpCirce,
  Dependencies.circeGeneric,
  Dependencies.circeCore,
  Dependencies.circeJawn,
  Dependencies.circeParser,
  Dependencies.circeOptics,
  Dependencies.akkaHttpTestkit
)