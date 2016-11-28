package com.spark.test

import org.apache.hadoop.io.{Text, LongWritable}
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat
import org.apache.spark.{SparkContext, SparkConf}
import com.hadoop.CombineXmlInputFormat1

/**
 * Created by wangxy on 15-8-6.
 */
object CombineXml {

  def main(args: Array[String]): Unit ={

    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val Array(srcPath, tarPath) = args

    //    sc.hadoopConfiguration.set("mapred.max.split.size", (256*1024*1024).toString)
    //    sc.hadoopConfiguration.set("mapred.min.split.size", (256*1024*1024).toString)

//    conf.set("xmlinput.start", "<property>");
//    conf.set("xmlinput.end", "</property>");

    sc.hadoopConfiguration.set("mapreduce.input.fileinputformat.split.maxsize", (256*1024*1024).toString)
    sc.hadoopConfiguration.set("xmlinput.start", "<property>")
    sc.hadoopConfiguration.set("xmlinput.end", "</property>")

//    sc.wholeTextFiles(tarPath,1)

    val file = sc.newAPIHadoopFile[LongWritable, Text, CombineXmlInputFormat1](srcPath)
    file.saveAsTextFile(tarPath)
  }

}
