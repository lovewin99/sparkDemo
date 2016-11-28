package com.test

import com.tools.RedisUtils
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by wangxy on 16-3-7.
 */
object TestSJL {
  def process(filePath: String): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    /** 读取HDFS上的文件 */
    //    val lines = sc.textFile(filePath)
    val lines = sc.textFile("sjl/test.txt")
    /** 处理数据形成(String(x+y),Array((a,b,c))) */
    val key2str = lines.map { line =>
      var result = ("", Array[(String, String, String)]())
      val x = line.split(",", -1)
      if (x.length == 11) {
        /** 经纬度转换栅格 */
        //        val grid = CoorTransformUtils.wgs2Mercator(x(2).toDouble, x(3).toDouble)
        //        val gridStr = Math.rint(grid._1 / 50).toInt + "," + Math.rint(grid._2 / 50).toInt
        val gridStr = x(2) + "," + x(3)
        /** */
        val str = Array((x(6) + "," + x(7), x(9), x(10)))
        result = (gridStr, str)
      }

      /** 返回值 */
      result
    }.filter(_._1 != "")

    println("----------------------------------------")
    /** 按key分组，value合并成一个大数组 */
    key2str.reduceByKey((x, y) => x ++ y).foreachPartition { p =>
      val result = p.map { x =>
        val key = x._1
        val value = x._2.groupBy(_._1).map { y =>
          val key1 = y._1
          val arr = y._2.toArray.map(_._2.toInt)
          val avg = arr.sum / arr.length
          val count = arr.length
          val max = y._2.toArray.map(_._3).max
          key1 + "," + avg + "," + count + "," + max
        }.mkString("|")
        println((key,value))
        (key, value)
      }.toMap


      for((x,y) <- result) println(x +"------>" + y)
      /** Insert data to Redis */
      RedisUtils.putMap2RedisTable("sjltest", result)
    }
  }

  def main(args: Array[String]) {
    if (args.length < 1) {
      System.err.println("Usage : <file>")
      System.exit(1)
    }

    process(args(0))
  }
}
