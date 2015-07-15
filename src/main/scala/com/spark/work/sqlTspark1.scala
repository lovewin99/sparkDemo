package com.spark.work

import org.apache.spark.{SparkContext, SparkConf}
import java.text.SimpleDateFormat

/**
 * Created by wangxy on 15-7-14.
 */
object sqlTspark1 {
  private def uTimeToHour(uTime : String) : String = {
    val timestamp = uTime.toLong * 1000
    val simpleDateFormat = new SimpleDateFormat("HH")
    simpleDateFormat.format(new java.util.Date(timestamp))
  }

  private def uTimeToData(uTime : String) : String = {
    val timestamp = uTime.toLong * 1000
    val simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss")
    simpleDateFormat.format(new java.util.Date(timestamp))
  }

  private def mapProcess(value: String):(String, String) = {
    val fields = value.split('\01')
    if (fields.length != 39)
      ("-1", "-1")
    else {
      val log_id = fields(0)
      val grid_id = fields(1)
      val cell_auid = fields(2)
      val start_date = fields(3)
      val end_date = fields(4)
      val prov_id = fields(5)
      val city_id = fields(6)
      val area_id = fields(7)
      val proj_id = fields(8)
      val proj_type = fields(9)
      val work_id = fields(10)
      val log_type = fields(11)
      val mob_type = fields(12)
      val in_out = fields(13)
//      val net_type
      ("1", "1")
    }
  }

  def main(args: Array[String]) {

    if (args.length != 2) {
      System.err.println("Usage: <in-file> <out-file>")
      System.exit(1)
    }

    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val textRDD = sc.textFile(args(0))

    textRDD.cache()

    val result = textRDD.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_+_)

    result.saveAsTextFile(args(1))
  }
}
