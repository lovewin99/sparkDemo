package com.spark.test

import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * Created by wangxy on 16-11-11.
 */
object TestCapacity {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext("local", "TestCapacity", conf)

    val rdd = sc.parallelize(Seq(1,2,3,4,5,1,2,3,4,5))

    rdd.map{x=>
      Thread.sleep(5000)
      (x, 1)
    }.groupByKey().map{x =>
      val num = x._2.sum
      Thread.sleep(5000)
      s"${x._1},$num"
    }.saveAsTextFile(args(0))

  }

}
