package com.spark.test

/**
 * Created by wangxy on 15-5-19.
 */

import org.apache.spark.{SparkConf, SparkContext}

/**
 * 统计字符出现次数
 */
object WordCount {
  def main(args: Array[String]) {

    if (args.length != 2) {
      System.err.println("Usage: <in-file> <out-file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    sc.setLogLevel("WARN")
    val textRDD = sc.textFile(args(0))
//    val textRdd = sc.textFile(args(1))
//    textRDD.cache()\

    val result = textRDD.flatMap(_.split("\\|")).map((_, 1)).reduceByKey(_+_)

//    val result = textRDD.flatMap(_.split(" ")).map((_, 1)).join(textRdd.flatMap(_.split(" ")).map((_, 1))).map(e => e.)
    result.saveAsTextFile(args(1))
  }
}