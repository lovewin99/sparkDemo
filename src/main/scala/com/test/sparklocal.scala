package com.test

import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * Created by wangxy on 16-7-20.
 */
object sparklocal {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext("local", "sparklocal", conf)

    val rdd = sc.textFile("/home/wangxy/data/hallo.txt").coalesce(30)

    rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).saveAsTextFile("/home/wangxy/testsv1/")

  }

}
