package com.test

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by wangxy on 16-5-18.
 */
object lhw {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val sc = new SparkContext("local", "lhw", conf)

    val rdd = sc.textFile("/home/wangxy/工作文档/上海/0518测试/imsi1/*")
    rdd.map{x=>
      val strArr = x.split(",", -1)
      (strArr.head, x)
    }.groupByKey().map{
      case (k, v) => {
        println(v.head)
        v.head
      }
    }.saveAsObjectFile("/home/wangxy/工作文档/上海/0518测试/3")
  }
}
