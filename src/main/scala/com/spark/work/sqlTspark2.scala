//package com.spark.work
//
//import java.util.Date
//import java.text.SimpleDateFormat
//
//import scala.util.parsing.json.JSON
//
//import org.apache.spark.{SparkContext, SparkConf}
//import org.apache.spark.sql.hive.HiveContext
//import scala.collection.immutable.Map
//
//
///**
// * Created by wangxy on 15-7-14.
// */
//object sqlTspark2 {
//
//  // 活取当前时间
//  def nowTime() : String = {
//    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    sdf.format(new Date)
//  }
//
//  // 转小时
//  def uTimeToHour(uTime : Double) : String = {
//    val timestamp = uTime.toLong * 1000
//    val simpleDateFormat = new SimpleDateFormat("HH")
//    simpleDateFormat.format(new java.util.Date(timestamp))
//  }
//
//  // 转时间
//  def uTimeToData(uTime : Double) : String = {
//    val timestamp = uTime.toLong * 1000
//    val simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//    simpleDateFormat.format(new java.util.Date(timestamp))
//  }
//
//  def myfilter1(str: String) : Boolean = {
//    val res = JSON.parseFull(str)
//    res match{
//      case None => false
//      case Some(map :Map[String, Any])=>{
//        map.size match {
//          case 39 => {
//            val part = map.getOrElse("part", "-1").asInstanceOf[String]
//            part match {
//              case "3" => true
//              case _ => {
//                map.foreach(e => println("key = " + e._1 + "    value = " + e._2))
//                false
//              }
//            }
//          }
//          case _ => false
//        }
//      }
//      case _ => false
//    }
//  }
//
//  def myfilter2(str: String) : Boolean = {
//    val strArr = str.split(",")
//    strArr(0) match{
//      case "0" => false
//      case _ => true
//    }
//  }
//
//
//  def mapProcess(str: String):(String, String) = {
//
//    val res = JSON.parseFull(str)
//    println("res = " + res)
//    res match{
//      case None => ("-1", "-1")
//      case Some(map :Map[String, Any])=>{
//        println("log_id = " + map.getOrElse("floor_id", 0))
//        println("grid_id = " + map.getOrElse("build_id", 0))
//        println("test_type = " + map.getOrElse("test_type", 0))
//        val start_date  =  map.getOrElse("start_date", 0.0).asInstanceOf[Double].toInt
//        val hour_id     =  uTimeToHour(start_date)
//        val date_id     =  uTimeToData(start_date)
//        val log_id      =  map.getOrElse("log_id", 0.0).asInstanceOf[Double].toInt
//        val grid_id     =  map.getOrElse("grid_id", 0.0).asInstanceOf[Double].toInt
//        val cell_auid   =  map.getOrElse("cell_auid", "0").asInstanceOf[String]
//        val end_date    =  map.getOrElse("end_date", 0.0).asInstanceOf[Double].toInt
//        val prov_id     =  map.getOrElse("prov_id", 0.0).asInstanceOf[Double].toInt
//        val city_id     =  map.getOrElse("city_id", 0.0).asInstanceOf[Double].toInt
//        val area_id     =  map.getOrElse("area_id", 0.0).asInstanceOf[Double].toInt
//        val proj_id     =  map.getOrElse("proj_id", 0.0).asInstanceOf[Double].toInt
//        val proj_type   =  map.getOrElse("proj_type", 0.0).asInstanceOf[Double].toInt
//        val work_id     =  map.getOrElse("work_id", 0.0).asInstanceOf[Double].toInt
//        val log_type    =  map.getOrElse("log_type", 0.0).asInstanceOf[Double].toInt
//        val mob_type    =  map.getOrElse("mob_type", 0.0).asInstanceOf[Double].toInt
//        val in_out      =  map.getOrElse("in_out", 0.0).asInstanceOf[Double].toInt
//        val net_type    =  map.getOrElse("net_type", 0.0).asInstanceOf[Double].toInt
//
//        val floor_id    =  map.getOrElse("floor_id", "0").asInstanceOf[String]
//        val build_id    =  map.getOrElse("build_id", "0").asInstanceOf[String]
//        val map_lati    =  map.getOrElse("map_lati", 0.0).asInstanceOf[Double].toFloat
//        val map_longi   =  map.getOrElse("map_longi", 0.0).asInstanceOf[Double].toFloat
//        val build_lvl   =  map.getOrElse("build_lvl", 0.0).asInstanceOf[Double].toInt
//        val test_type   =  map.getOrElse("test_type", "0").asInstanceOf[String]
//        val scene_type  =  map.getOrElse("scene_type", 0.0).asInstanceOf[Double].toInt
//        val algo_set_id =  map.getOrElse("algo_set_id", 0.0).asInstanceOf[Double].toInt
//        val rxlevsub    =  map.getOrElse("rxlevsub", 0.0).asInstanceOf[Double].toFloat
//        val c_i         =  map.getOrElse("c_i", 0.0).asInstanceOf[Double].toFloat
//        val totalrscp   =  map.getOrElse("totalrscp", 0.0).asInstanceOf[Double].toFloat
//        val totalec_io  =  map.getOrElse("totalec_io", 0.0).asInstanceOf[Double].toFloat
//        val tx_power    =  map.getOrElse("tx_power", 0.0).asInstanceOf[Double].toFloat
//        val bestrscp    =  map.getOrElse("bestrscp", 0.0).asInstanceOf[Double].toFloat
//        val bestec_io   =  map.getOrElse("bestec_io", 0.0).asInstanceOf[Double].toFloat
//        val idt_id      =  map.getOrElse("idt_id", 0.0).asInstanceOf[Double].toInt
//        val idt_dim_val =  map.getOrElse("idt_dim_val", 0.0).asInstanceOf[Double]
//        val idt_val     =  map.getOrElse("idt_val", 0.0).asInstanceOf[Double]
//        val upd_time    =  nowTime
//        val test_item_type  = "-1"
//        val idt_id_11043    = 11043
//
//        var idt_form_11043 : Double = 0
//        if (80051 == idt_id){
//          idt_form_11043 = idt_dim_val * idt_val / 1000
//        }
//
//
////        val key = start_date.toString + ","
////        val value = start_date.toString
//
//        val key = log_id + "," + grid_id + "," + cell_auid + "," + start_date + "," + end_date + "," +
//          prov_id + "," + city_id + "," + area_id + "," + proj_id + "," + proj_type + "," + work_id + "," + log_type + "," +
//          mob_type + "," + in_out + "," + net_type + "," + floor_id + "," + build_id + "," + map_lati + "," + map_longi + "," +
//          build_lvl + ","  + test_type + ","  + scene_type + "," + algo_set_id + "," + rxlevsub + "," + c_i + "," +
//          totalrscp + "," + totalec_io + "," + tx_power + "," + bestrscp + "," + bestec_io
//
//        val value = "1" + ","  + date_id + ","  + hour_id + ","  + test_item_type + ","  + log_id + ","  + start_date + ","  +
//          end_date + ","  + prov_id + ","  + city_id + ","  + area_id + ","  + proj_id + ","  + proj_type + ","  + work_id + ","  +
//          log_type + ","  + mob_type + ","  + in_out + ","  + scene_type + ","  + net_type + ","  + "NULL" + ","  + "NULL" + "," +
//          algo_set_id + "," + idt_id_11043 + "," + idt_form_11043.toString + "," + upd_time
//        (key, value)
//      }
//      case _ => ("-1", "-1")
//    }
//  }
//
//
//
//  def reduceProcess(str: String, iter: Iterable[String]) : String = {
//    var sum: Double = 0
//    var strArr = Array("0")
//    var bFlag = false
//    iter.foreach(e =>{
//      strArr = e.split(',')
//      if(strArr.length == 24){
//        bFlag = true
//        sum += strArr(22).toDouble
//      }
//    })
//    if(bFlag)
//      strArr.update(22, sum.toString)
//    strArr.mkString(",")
//  }
//
//  def main(args: Array[String]) {
//
//    if (args.length != 1) {
//      System.err.println("Usage: <in-file> <out-file>")
//      System.exit(1)
//    }
//
//    val Array(path) = args
//
//    val conf = new SparkConf().
//                      setAppName("sqlTspark").
//                      set("spark.executor.memory","1g").
//                      set("spark.cores.max","2").
//                      setJars(List(SparkContext.jarOfClass(this.getClass).getOrElse("")))
//
//    val sc = new SparkContext(conf)
//
//    val hivesql = new HiveContext(sc)
//
//    val df = hivesql.sql("select * from tmp_dp.testcell where part='3' limit 10")
//
////    val res = df.toSchemaRDD.mapPartitions(Iter => {Iter.map(e => mapProcess(e))})
////      .saveAsTextFile(path)
//
//    val res = df.toJSON.mapPartitions(_.map(mapProcess)).filter(_._1 != "-1").
//      groupByKey().mapPartitions(_.map(e => reduceProcess(e._1, e._2))).filter(myfilter2).saveAsTextFile(path)
//
//    }
//}
