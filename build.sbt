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
