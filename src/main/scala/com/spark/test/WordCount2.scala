package com.spark.test

/**
 * Created by wangxy on 15-5-22.
 */

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 统计字符出现次数
 */
object WordCount2 {
  def main(args: Array[String]) {

    if (args.length != 3) {
      System.err.println("Usage: <in-file> <out-file>")
      System.exit(1)
    }


    val conf = new SparkConf()
    val sc = new SparkContext(conf)

//    FileSystem fs = FileSystem.
    val textRDD = sc.textFile(args(0))

    val result = textRDD.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)

//    val textRDD1 = sc.textFile(args(1)+"/*")
//  result.flatMap()

    val result1 = result.map(x => x._1).map((_, 1)).reduceByKey(_+_)
//    val result1 = result.map((_, 1)).reduceByKey(_+_)
    result.saveAsTextFile(args(1))
    result1.saveAsTextFile(args(2))

  }
}