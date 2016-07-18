package com.spark.test

/**
 * Created by wangxy on 15-10-26.
 */

import org.apache.spark.{SparkConf, SparkContext}

object pipe {

  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      System.err.println("Usage: <in-file> <app-file> <out-file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val Array(strpath, apppath, tarpath) = args
    val a = sc.wholeTextFiles(args(0))
    a.map{x =>
      println(x._1)
      x._2
    }.pipe(apppath).saveAsTextFile(tarpath)

  }

}
