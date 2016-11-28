package com.spark.test

import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * Created by wangxy on 16-11-7.
 */
object Tsz {

  def main(args: Array[String]): Unit  = {
    val conf = new SparkConf()
    val sc = new SparkContext("local", "Tsz", conf)

    val rdd1 = sc.parallelize(Seq("hello world", "hello spark"))
    val rdd2 = sc.parallelize(Seq("hello everyone", "go go"))

    val nr1 = rdd1.flatMap(_.split(" ", -1)).map((_, 1))
    val nr2 = rdd2.flatMap(_.split(" ", -1)).map((_, 1))
    nr1.cogroup(nr2).foreach{x => println(s"${x._1} 1=${x._2._1.mkString(",")} 2=${x._2._2.mkString(",")}")}
//    nr1.join(nr2).foreach{x => println(x)}
  }

}
