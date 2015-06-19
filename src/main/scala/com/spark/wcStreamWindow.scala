package com.spark

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.SparkContext._

/**
 * Created by wangxy on 15-6-19.
 */
object wcStreamWindow {

  def main(args: Array[String]) :Unit={
    if(args.length != 5){
      System.err.println("Usage: <host> <port> <dir> <tm1> <tm2> <tm4>")
      System.exit(1)
    }

    val Array(host, port, dir, tm1, tm2, tm3) = args

    val conf = new SparkConf()
    conf.setMaster("spark://cloud38:7077")
      .setAppName("wcStreaming")
      .set("spark.executor.memory","1g")
      .setJars(List(SparkContext.jarOfClass(this.getClass).getOrElse("")))

    val ssc = new StreamingContext(conf,Seconds(tm1.toInt))
    ssc.checkpoint(dir)

    val lines = ssc.socketTextStream(host, port.toInt, StorageLevel.MEMORY_ONLY_SER)
    val words = lines.flatMap(_.split(" "))

    //val wordCounts = words.map(x => (x , 1)).reduceByKeyAndWindow((x : Int, y : Int) => x + y, Seconds(5), Seconds(5))
    val wordCounts = words.map(x => (x , 1)).reduceByKeyAndWindow(_+_, _-_,Seconds(tm2.toInt), Seconds(tm3.toInt))

    val sortedWordCount = wordCounts.map{
      case (char,count) => (count,char)
    }.transform(_.sortByKey(false)).map{
      case (char,count) => (count,char)
    }

    sortedWordCount.print()
    ssc.start()
    ssc.awaitTermination()
  }

}
