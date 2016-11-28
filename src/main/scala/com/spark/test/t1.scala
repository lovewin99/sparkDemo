package com.spark.test

import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * Created by wangxy on 16-7-19.
 */
object t1 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(args(0))
    rdd.map{x =>
      val strArr = x.split(",", -1)
      (1, strArr)
    }.groupByKey().map{
      case(k, v) =>
        val l = v.toList.distinct
        ""
    }

    val s1 = "123"
    s1.toByte

  }

}
