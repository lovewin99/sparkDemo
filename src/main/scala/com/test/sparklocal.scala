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

    val rdd = sc.parallelize(Seq(1,2,3,4))
    rdd.collect().foreach(println)

//    val rdd = sc.textFile("/home/wangxy/data/hallo.txt").coalesce(30)

//    rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_).collect().foreach(println)

  }

}
