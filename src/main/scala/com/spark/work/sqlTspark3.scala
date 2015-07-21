package com.spark.work

import java.util.Date
import java.text.SimpleDateFormat

import scala.util.parsing.json.JSON

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.sql.hive.HiveContext
import scala.collection.immutable.Map

import org.json.JSONObject

/**
 * Created by wangxy on 15-7-20.
 */
object sqlTspark3 {
  // 活取当前时间
  def nowTime() : String = {
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    sdf.format(new Date)
  }

  // 转小时
  def uTimeToHour(uTime : Double) : String = {
    val timestamp = uTime.toLong * 1000
    val simpleDateFormat = new SimpleDateFormat("HH")
    simpleDateFormat.format(new java.util.Date(timestamp))
  }

  // 转时间
  def uTimeToData(uTime : Double) : String = {
    val timestamp = uTime.toLong * 1000
    val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    simpleDateFormat.format(new java.util.Date(timestamp))
  }

  def myfilter1(str: String) : Boolean = {
    val res = JSON.parseFull(str)
    res match{
      case None => false
      case Some(map :Map[String, Any])=>{
        map.size match {
          case 39 => {
            val part = map.getOrElse("part", "-1").asInstanceOf[String]
            part match {
              case "3" => true
              case _ => {
                map.foreach(e => println("key = " + e._1 + "    value = " + e._2))
                false
              }
            }
          }
          case _ => false
        }
      }
      case _ => false
    }
  }

  def myfilter2(str: String) : Boolean = {
    val strArr = str.split(",")
    strArr(0) match{
      case "0" => false
      case _ => true
    }
  }

  def mapProcess(str: String):(String, String) = {

    val ojson = new JSONObject(str)

    val start_date  =  ojson.optString("start_date", "")
    val hour_id     =  uTimeToHour(start_date.toDouble)
    val date_id     =  uTimeToData(start_date.toDouble)
    val log_id      =  ojson.optString("log_id", "")
    val grid_id     =  ojson.optString("grid_id", "")
    val cell_auid   =  ojson.optString("cell_auid", "")
    val end_date    =  ojson.optString("end_date", "")
    val prov_id     =  ojson.optString("prov_id", "")
    val city_id     =  ojson.optString("city_id", "")
    val area_id     =  ojson.optString("area_id", "")
    val proj_id     =  ojson.optString("proj_id", "")
    val proj_type   =  ojson.optString("proj_type", "")
    val work_id     =  ojson.optString("work_id", "")
    val log_type    =  ojson.optString("log_type", "")
    val mob_type    =  ojson.optString("mob_type", "")
    val in_out      =  ojson.optString("in_out", "")
    val net_type    =  ojson.optString("net_type", "")

    val floor_id    =  ojson.optString("floor_id", "")
    val build_id    =  ojson.optString("build_id", "")
    val map_lati    =  ojson.optString("map_lati", "")
    val map_longi   =  ojson.optString("map_longi", "")
    val build_lvl   =  ojson.optString("build_lvl", "")
    val test_type   =  ojson.optString("test_type", "")
    val scene_type  =  ojson.optString("scene_type", "")
    val algo_set_id =  ojson.optString("algo_set_id", "")
    val rxlevsub    =  ojson.optString("rxlevsub", "")
    val c_i         =  ojson.optString("c_i", "")
    val totalrscp   =  ojson.optString("totalrscp", "")
    val totalec_io  =  ojson.optString("totalec_io", "")
    val tx_power    =  ojson.optString("tx_power", "")
    val bestrscp    =  ojson.optString("bestrscp", "")
    val bestec_io   =  ojson.optString("bestec_io", "")
    val idt_id      =  ojson.optInt("idt_id", 0)
    val idt_dim_val =  ojson.optDouble("idt_dim_val", 0)
    val idt_val     =  ojson.optDouble("idt_val", 0)
    val upd_time    =  nowTime
    val test_item_type  = "-1"
    val idt_id_11043    = 11043

    var idt_form_11043 : Double = 0
    if (80051 == idt_id){
      idt_form_11043 = idt_dim_val * idt_val / 1000
    }

    val key = log_id + "," + grid_id + "," + cell_auid + "," + start_date + "," + end_date + "," +
      prov_id + "," + city_id + "," + area_id + "," + proj_id + "," + proj_type + "," + work_id + "," + log_type + "," +
      mob_type + "," + in_out + "," + net_type + "," + floor_id + "," + build_id + "," + map_lati + "," + map_longi + "," +
      build_lvl + ","  + test_type + ","  + scene_type + "," + algo_set_id + "," + rxlevsub + "," + c_i + "," +
      totalrscp + "," + totalec_io + "," + tx_power + "," + bestrscp + "," + bestec_io

    val value = "1" + ","  + date_id + ","  + hour_id + ","  + test_item_type + ","  + log_id + ","  + start_date + ","  +
      end_date.toString + ","  + prov_id + ","  + city_id + ","  + area_id + ","  + proj_id + ","  + proj_type + ","  + work_id + ","  +
      log_type + ","  + mob_type + ","  + in_out + ","  + scene_type + ","  + net_type + ","  + "NULL" + ","  + "NULL" + "," +
      algo_set_id + "," + idt_id_11043 + "," + idt_form_11043 + "," + upd_time
    (key, value)
  }



  def reduceProcess(str: String, iter: Iterable[String]) : String = {
    var sum: Double = 0
    var strArr = Array("0")
    var bFlag = false
    iter.foreach(e =>{
      strArr = e.split(',')
      if(strArr.length == 24){
        bFlag = true
        sum += strArr(22).toDouble
      }
    })
    if(bFlag)
      strArr.update(22, sum.toString)
    strArr.mkString(",")
  }

  def main(args: Array[String]) {

    if (args.length != 1) {
      System.err.println("Usage: <in-file> <out-file>")
      System.exit(1)
    }

    val Array(path) = args

    val conf = new SparkConf().
      setAppName("sqlTspark").
//      set("spark.executor.memory","1g").
//      set("spark.cores.max","2").
      setJars(List(SparkContext.jarOfClass(this.getClass).getOrElse("")))

    val sc = new SparkContext(conf)

    val hivesql = new HiveContext(sc)

    val df = hivesql.sql("select * from tmp_dp.t_i_grid_cell where part in ('3')")

    //    val res = df.toSchemaRDD.mapPartitions(Iter => {Iter.map(e => mapProcess(e))})
    //      .saveAsTextFile(path)

    val res = df.toJSON.mapPartitions(_.map(mapProcess)).filter(_._1 != "-1").
      groupByKey().mapPartitions(_.map(e => reduceProcess(e._1, e._2))).filter(myfilter2).saveAsTextFile(path)

  }
}
