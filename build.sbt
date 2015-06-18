name := "sparkDemo"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.0" % "test" ,
  "junit" % "junit" % "4.10" % "provided" ,
  "redis.clients" % "jedis" % "2.1.0",

  "org.apache.spark" % "spark-assembly_2.10" % "1.0.1" % "provided",
  "org.apache.spark" % "spark-core_2.10" % "1.1.0",
  "org.apache.spark" % "spark-mllib_2.10" % "1.1.0",

  "org.apache.spark" % "spark-streaming_2.10" % "1.3.1",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.3.1",
  "org.apache.spark" % "spark-sql_2.10" % "1.3.1"

)

resolvers += "Nexus Repository" at "http://101.251.236.34:8081/nexus/content/groups/scalasbt/"

externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)
