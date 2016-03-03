package com.spark.test

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkContext, SparkConf}

import org.apache.spark._
import org.apache.spark.sql._
import org.apache.spark.sql.functions._


/**
 * Created by wangxy on 16-2-25.
 */
object DataFrame {

  case class Person(name: String, age: String)

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()

    val ssc = new StreamingContext(conf, Seconds(5))
    val sqlContext = new org.apache.spark.sql.SQLContext(ssc.sparkContext)
    import sqlContext.implicits._

//    val text = sc.textFile(args(0)).map(_.split(",")).map(p => Person(p(0), p(1))).toDF()

    val lines =ssc.textFileStream(args(0))

    lines.foreachRDD{line=>

      val df = line.map{_.split(" ")}.map(p => Person(p(0), p(1))).toDF()
//      df.save("wxy/df1", mode=SaveMode.Append)
      df.write.mode(SaveMode.Append).format("txt").save("wxy/df1")
    }

    ssc.start()
    ssc.awaitTermination()

  }
}
