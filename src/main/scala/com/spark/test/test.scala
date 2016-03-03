package com.spark.test

import java.text.SimpleDateFormat
import java.util.Date

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
//import sqlContext.implicits._
//import org.apache.spark.sql.SQLContext.implicits._
//import math._

//import sqlContext.implicits._

/**
 * Created by wangxy on 15-12-29.
 */
object test {

  def getNowTime(): String = {
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    dateFormat.format(now)
  }

  def kankan(n: Int): Int = {
    if(n <= 1){
      1
    }else{
      n*kankan(n-1)
    }
  }

  def main(args: Array[String]): Unit = {
    if(args.length != 3){
      System.err.println("Usage: <dir> <out-file> <checkpoint>")
      System.exit(1)
    }

    case class Person(name: String, age: String)

    val Array(dir, out, ck) = args

    val conf = new SparkConf()
    val ssc = new StreamingContext(conf, Seconds(5))

    val lines =ssc.textFileStream(dir)

//    val ds = Seq((1, 2, 3)).toDF()
//
//    lines.foreachRDD(rdd =>{
//      val t1 = rdd.map(_.split(" ")).map(x => Person(x(0),x(1)).toDF())
//    }
//      _.saveAsTextFile(out+"/"+getNowTime().slice(0,8))
//    )
    //    val lines = ssc.socketTextStream(serverIP, serverPort)

//    val result = lines.flatMap(_.split(" ")).map(word => (word, 1)).reduceByKey(_+_)


//    result.saveAsTextFiles(out)

//    ssc.checkpoint(ck)

    ssc.start()
    ssc.awaitTermination()
  }
}
