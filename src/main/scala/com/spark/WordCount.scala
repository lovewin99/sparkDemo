package com.spark

/**
 * Created by wangxy on 15-5-19.
 */

import org.apache.spark.{SparkConf,SparkContext}
import SparkContext._

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
    val textRDD = sc.textFile(args(0))
    textRDD.cache()

    val result = textRDD.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)
    result.saveAsTextFile(args(1))
  }
}