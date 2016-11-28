package com.spark.work

import java.io.{File, PrintWriter}

import com.tools.RedisUtils
import org.apache.spark.{SparkContext, SparkConf}

/**
 *
 * Created by wangxy on 16-8-12.
 */
object ExFingerLib {

  def main(args: Array[String]): Unit = {
//    val conf = new SparkConf()
//    val sc = new SparkContext("local", "ExFingerLib", conf)
    val writer = new PrintWriter(new File("/home/wangxy/data/outbc.csv"),"UTF-8")
    val m = RedisUtils.getResultMap("yuanqu0901")

    m.foreach{
      case (k, v) =>
        val a = k.replaceAll("[\\|\\$]", ",") + "," + v.replaceAll("[\\|\\$]", ",")
        writer.println(a)
    }


  }

}
