package com.spark.test

import java.text.SimpleDateFormat
import java.util.{Calendar, Properties}

import kafka.producer.{KeyedMessage, Producer, ProducerConfig}

//import scala.collection.mutable.Map
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.immutable.Map

/**
 * Created by wangxy on 15-6-30.
 */

//case object strContextSingleton {
//  @transient private var instance: StreamingContext = null
//
//  // Instantiate SQLContext on demand
//  def getInstance(conf: SparkConf, dur: Duration): StreamingContext = synchronized {
//    if (instance == null) {
//      instance = new StreamingContext(conf, dur)
//    }
//    instance
//  }
//}
//
//class tools extends Serializable{
//  def timename() : String = {
//      val today = Calendar.getInstance().getTime()
//      val time1 = new SimpleDateFormat("yyyyMMddHHmm")
//      val time2 = new SimpleDateFormat("ss")
//      var nss = time2.format(today).toInt
//      val yu = nss % 20
//      nss = nss - yu
//      if(nss == 0)
//        time1.format(today) + "00"
//      else
//        time1.format(today) + nss.toString
//  }
//}

/**
 * spark-submit --total-executor-cores 8 --master spark://master2:7077 --jars spark-streaming-kafka_2.10-1.3.1.jar --class com.spark.mConsumer sparkdemo_2.10-1.0.jar 20 slave1:2181 11 test95 1 hdfs://master1:8020/user/tescomm/wxy
 */

object mConsumer {

  def timename() : String = {
    val today = Calendar.getInstance().getTime()
    val time1 = new SimpleDateFormat("yyyyMMddHHmm")
    val time2 = new SimpleDateFormat("ss")
    var nss = time2.format(today).toInt
    val yu = nss % 20
    nss = nss - yu
    if (nss == 0)
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
    val conf = new SparkConf().setAppName("Streaming Test").set("spark.streaming.receiver.writeAheadLog.enable", "true")
//    val conf = new SparkConf().setAppName("Streaming Test")
    val ssc = new StreamingContext(conf, Seconds(ssTime.toInt))
//    val ssc = strContextSingleton.getInstance(conf, Seconds(ssTime.toInt))

    ssc.checkpoint("hdfs://master1:8020/user/tescomm/checktmp" )
    val arrTopic = topics.split(",")
    arrTopic.foreach(etpc => {
      val topicMap = Map[String, Int]((etpc, numThreads.toInt))
      val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)

//      lines.saveAsTextFiles("hdfs://master1:8020/user/tescomm/wxy/" + random + "/", "")

      lines.foreachRDD(
        lrdd => {
          lrdd.repartition(1).saveAsTextFile(strPath + "/" + etpc + "/" + timename + "/")
        }
      )
    })

    ssc.start()
    ssc.awaitTermination()

  }
}

/*
  def main (args: Array[String]) {
    if (args.length < 6) {
      System.err.println("Usage: KafkaWordCount <ssTime> <zkQuorum> <group> <topics> <numThreads> <output>")
      System.exit(1)
    }

    val Array(ssTime, zkQuorum, group, topics, numThreads, strPath) = args
    val conf = new SparkConf().setAppName("Streaming Test").setExecutorEnv("spark.streaming.receiver.writeAheadLog.enable", "TRUE")
    val ssc = new StreamingContext(conf, Seconds(ssTime.toInt))
//    ssc.checkpoint("hdfs://master1:8020/user/tescomm/checkpoint")

    val arrTopic = topics.split(",")
    val mbroker = Map("metadata.broker.list" -> "master2:9092,slave1:9092,slave2:9092")
    arrTopic.foreach(etpc => {
//      val topicMap = Map[String, Int]((etpc, numThreads.toInt))
      val topicSet = Set(etpc)
//      val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
//      val lines = KafkaUtils.createDirectStream(ssc, mbroker, topicSet).map(_._2)
      val lines = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, mbroker, topicSet).map(_._2)
      lines.foreachRDD(rdd => {
        val offsetRanges = rdd.asInstanceOf[HasOffsetRanges]
      })

//      lines.foreachRDD(lrdd => lrdd.saveAsTextFile(strPath + "/" + etpc + "/" + timename + "/" ))
      lines.foreachRDD(lrdd => lrdd.repartition(1).saveAsTextFile(strPath + "/" + etpc + "/" + timename + "/"))
    })

    ssc.start()
    ssc.awaitTermination()

  }
}
*/

/**
 * `spark-submit --master spark://master2:7077 --class com.spark.Producer sparkdemo_2.10-1.0.jar  master2:9092 test21 10000 100`
 */
object Producer1 {
  def main (args: Array[String]) {
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCountProducer <metadataBrokerList> <topic> " +
        "<messagesPerSec> <wordsPerMessage>")
      System.exit(1)
    }

    val Array(brokers, topic, messagesPerSec, wordsPerMessage) = args

    // Zookeeper connection properties
    val props = new Properties()
    props.put("metadata.broker.list", brokers)
    props.put("serializer.class", "kafka.serializer.StringEncoder")

    val config = new ProducerConfig(props)
    val producer = new Producer[String, String](config)

    // Send some messages
    var value = 0
    while (true) {
      val messages = (1 to messagesPerSec.toInt).map { messageNum =>
        value += 1
        val str = value.toString
        new KeyedMessage[String, String](topic, topic + "  " +str)
      }.toArray

      producer.send(messages: _*)
      Thread.sleep(wordsPerMessage.toLong)
    }

  }
}
