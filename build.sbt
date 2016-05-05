name := "finagleExample"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq (
  "com.twitter" %% "finagle-http" % "6.35.0",
  "com.twitter" %% "finagle-mysql" % "6.35.0"
)
