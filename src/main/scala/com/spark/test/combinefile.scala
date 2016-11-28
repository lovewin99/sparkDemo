package com.spark.test

/**
 * Created by wangxy on 15-7-30.
 */

import org.apache.hadoop.io._
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat
import org.apache.spark.{SparkContext, SparkConf}

object combinefile {
  def main(args: Array[String]): Unit ={

    if (args.length != 2) {
      System.err.println("Usage: <in-file> <out-file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val textRDD = sc.textFile(args(0))
    val textRdd = sc.textFile(args(1))

//    sc.hadoopConfiguration.set("mapred.max.split.size", (256*1024*1024).toString)
//    sc.hadoopConfiguration.set("mapred.min.split.size", (256*1024*1024).toString)

    sc.hadoopConfiguration.set("mapreduce.input.fileinputformat.split.maxsize", (256*1024*1024).toString)


    val file = sc.newAPIHadoopFile[LongWritable, Text, CombineTextInputFormat](args(0))
    val result = file.flatMap(x => x._2.toString.split("\\|")).map((_, 1)).reduceByKey(_+_)
    result.saveAsTextFile(args(1))
//    file.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)

  }
}
