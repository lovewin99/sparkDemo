import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

assemblySettings

name := "sparkDemo"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "maven Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/"
)

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.2.0" % "test" ,
  "junit" % "junit" % "4.10" % "provided" ,
  "redis.clients" % "jedis" % "2.1.0",

  "org.apache.spark" % "spark-assembly_2.10" % "1.3.0-cdh5.4.0" % "provided",
  "org.apache.spark" % "spark-core_2.10" % "1.3.0-cdh5.4.0",
  "org.apache.spark" % "spark-mllib_2.10" % "1.3.0-cdh5.4.0",

  "org.apache.spark" % "spark-streaming_2.10" % "1.3.0-cdh5.4.0",
  "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.3.0-cdh5.4.0",
  "org.apache.spark" % "spark-sql_2.10" % "1.3.0-cdh5.4.0",

  "org.apache.hive" % "hive-exec" % "1.1.0-cdh5.4.0",
  "org.apache.hadoop" % "hadoop-yarn-api" % "2.6.0-cdh5.4.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % "2.6.0-cdh5.4.0",
  "org.apache.hadoop" % "hadoop-mapreduce-client-jobclient" % "2.6.0-cdh5.4.0",
  "org.json" % "json" % "20090211"
  //  "net.liftweb" % "lift-util_2.9.2" % "2.6.2"
)

//resolvers += "Nexus Repository" at "http://101.251.236.34:8081/nexus/content/groups/scalasbt/"

//resolvers += Resolver.url("cloudera", url("https://repository.cloudera.com/artifactory/cloudera-repos/."))
//
//resolvers += Resolver.url("MavenOfficial", url("http://repo1.maven.org/maven2"))
//
//resolvers += Resolver.url("springside", url("http://springside.googlecode.com/svn/repository"))
//
//resolvers += Resolver.url("jboss", url("http://repository.jboss.org/nexus/content/groups/public-jboss"))

//externalResolvers := Resolver.withDefaultResolvers(resolvers.value, mavenCentral = false)

mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
{
  case PathList("com", "esotericsoftware", "minlog", xs @ _*) => MergeStrategy.first
  case PathList("org", "jboss","netty", xs @ _*) => MergeStrategy.last
  case PathList("com", "google","common", xs @ _*) => MergeStrategy.last
  case PathList("com", "google","protobuf", xs @ _*) => MergeStrategy.last
  case PathList("javax", "xml","stream", xs @ _*) => MergeStrategy.first
  case PathList("com", "sun","jersey", xs @ _*) => MergeStrategy.last
  case PathList("org", "codehaus","jackson", xs @ _*) => MergeStrategy.last
  case PathList("com", "codahale", xs @ _*)=> MergeStrategy.first
  //  case PathList("com", "amazonaws", xs @ _*)=> MergeStrategy.first
  case PathList("org", "json", xs @ _*) => MergeStrategy.first
  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
  case PathList("org", "apache", xs @ _*)=> MergeStrategy.first
  case PathList("org", "eclipse", xs @ _*) => MergeStrategy.first
  case PathList("akka",  xs @ _*)=> MergeStrategy.first
  case PathList("parquet",  xs @ _*)=> MergeStrategy.first
  case PathList("jodd",  xs @ _*)=> MergeStrategy.last
  case PathList("au", "com", "bytecode", "opencsv",  xs @ _*)=> MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith "jboss-beans.xml" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "pom.properties" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "pom.xml" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "overview.html" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "plugin.xml" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "parquet.thrift" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "Log$1.class" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "Log.class" => MergeStrategy.filterDistinctLines
  case PathList(ps @ _*) if ps.last endsWith "jersey-module-version" => MergeStrategy.filterDistinctLines
  //  case PathList(ps @ _*) if ps.last endsWith "CSVParser.class" => MergeStrategy.last
  //  case PathList(ps @ _*) if ps.last endsWith "DisableAlarmActionsRequest.class" => MergeStrategy.filterDistinctLines
  case "application.conf" => MergeStrategy.concat
  case "unwanted.txt"     => MergeStrategy.discard
  case x => old(x)
}
}


//mainClass in assembly := Some("cn.com.gis.etl.GenFingerLib")

jarName in assembly := "sparkDemo.jar"
