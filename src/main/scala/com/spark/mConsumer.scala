package com.spark

import java.text.SimpleDateFormat
import java.util.Calendar

//import scala.collection.mutable.Map
import scala.collection.immutable.Map

import org.apache.spark.SparkConf
import org.apache.spark.sql.SaveMode
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by wangxy on 15-6-30.
 */
object mConsumer {

  def timename() : String = {
    val today = Calendar.getInstance().getTime()
    val time1 = new SimpleDateFormat("yyyyMMddHHmm")
    val time2 = new SimpleDateFormat("ss")
    var nss = time2.format(today).toInt
    val yu = nss % 20
    nss = nss - yu
    if(nss == 0)
      time1.format(today) + "00"
    else
      time1.format(today) + nss.toString
  }

  def main (args: Array[String]) {
    if (args.length < 6) {
      System.err.println("Usage: KafkaWordCount <ssTime> <zkQuorum> <group> <topics> <numThreads> <output>")
      System.exit(1)
    }

    val Array(ssTime, zkQuorum, group, topics, numThreads, strPath) = args
    val conf = new SparkConf().setAppName("Streaming Test")
    val ssc = new StreamingContext(conf, Seconds(ssTime.toInt))
    //    ssc.checkpoint("checkpoint1")

//    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val arrTopic = topics.split(",")
    arrTopic.foreach(e => {
      val topicMap = Map[String, Int]((e, numThreads.toInt))
//      val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
      val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
      val filename = timename

      println("\n\n" + filename + "\n\n")
      lines.saveAsTextFiles(strPath + "/" + e + "/" + filename + "/", null)
    })

    ssc.start()
    ssc.awaitTermination()

  }
}
