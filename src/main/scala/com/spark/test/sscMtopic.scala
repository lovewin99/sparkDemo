package com.spark.test

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils

/**
 *
 * Created by wangxy on 16-5-24.
 */
object sscMtopic {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val ssc = new StreamingContext(conf, Seconds(30))
    val topicMap = Map("wt1"->1, "wt2"->1, "wt3"->1)
    val lines = KafkaUtils.createStream(ssc, "cloud136:2181,cloud138:2181,cloud139:2181", "1", topicMap).map(_._2)
    lines.saveAsTextFiles("wxy/tssc/")
    ssc.start()
    ssc.awaitTermination()
  }

}
