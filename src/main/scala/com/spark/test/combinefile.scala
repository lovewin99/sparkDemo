package com.spark.test

/**
 * Created by wangxy on 15-7-30.
 */

import org.apache.hadoop.io._
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat
import org.apache.spark.{SparkContext, SparkConf}

object combinefile {
  def main(args: Array[String]): Unit ={

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

//    sc.hadoopConfiguration.set("mapred.max.split.size", (256*1024*1024).toString)
//    sc.hadoopConfiguration.set("mapred.min.split.size", (256*1024*1024).toString)

    sc.hadoopConfiguration.set("mapreduce.input.fileinputformat.split.maxsize", (256*1024*1024).toString)


    val file = sc.newAPIHadoopFile[LongWritable, Text, CombineTextInputFormat]("hdfs://tescomm/user/hive/warehouse/tmp_dp.db/testcell/part=t11[12]*")
    file.count
  }
}
