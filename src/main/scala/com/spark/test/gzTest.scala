package com.spark.test

import org.apache.hadoop.mapreduce.lib.input._
import org.apache.spark.{SparkContext, SparkConf}
import org.apache.hadoop.io.{LongWritable, Text}

/**
 * Created by wangxy on 16-4-12.
 */
object gzTest {
  def main(args: Array[String]): Unit = {
    if (args.length != 3) {
      System.err.println("Usage: <in-file> <app-file> <out-file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

//    val Array(srcPath, apppath, tarpath) = args
//    val file = sc.newAPIHadoopFile[LongWritable, Text, FileInputFormat](srcPath)
//    val a = sc.wholeTextFiles(args(0))
//    a.map(_._2).pipe(apppath).collect()

  }
}
