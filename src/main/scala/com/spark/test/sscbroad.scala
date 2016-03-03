package com.spark.test

/**
 * Created by wangxy on 15-12-21.
 */

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object sscbroad {
  def main(args: Array[String]) :Unit={
    //    if(args.length != 3){
    //      println("args:<sparkUrl> <sparkHome> <jarFile>")
    //    }
    //    val sparkUrl = args(0)
    //    val sparkHome = args(1)
    //    val jarFile = args(2)
    //
    //    val ssc = new StreamingContext(sparkUrl, "wcStreaming", Seconds(1), sparkHome, Seq(jarFile))
    //
    //    val tweets = TwitterUtils.createStream(ssc, None)
    //
    //    val statuses = tweets.map(status => status.getText())
    //    statuses.print()
    //
    //    ssc.checkpoint(checkpointDir)
    //
    //    ssc.start
    //    ssc.awaitTermination

    //    if(args.length != 2){
    //      println("args: <serverIP> <serverPort>")
    //    }

    if(args.length != 3){
      System.err.println("Usage: <dir> <out-file> <checkpoint>")
      System.exit(1)
    }

    //    val serverIP = args(0)
    //    val serverPort = args(1).toInt

    val Array(dir, out, ck) = args

    val conf = new SparkConf()
    conf.setMaster("spark://cloud138:7077")
      .setAppName("sscbroad")
      .set("spark.executor.memory","1g")
      .setJars(List(SparkContext.jarOfClass(this.getClass).getOrElse("")))

    val ssc = new StreamingContext(conf, Seconds(1))

    val lines =ssc.textFileStream(dir)
    lines.foreachRDD(rdd => rdd.saveAsTextFile(""))
    //    val lines = ssc.socketTextStream(serverIP, serverPort)

    val result = lines.flatMap(_.split(" ")).map(word => (word, 1)).reduceByKey(_+_)

    result.saveAsTextFiles(out)

    ssc.checkpoint(ck)

    ssc.start
    ssc.awaitTermination

  }
}
