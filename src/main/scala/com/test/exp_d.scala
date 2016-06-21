package com.test

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.spark.{SparkContext, SparkConf}

/**
 * g_user_exp_d.sql h_basic_user_exp_d.sql h_basic_cell_exp_d.sql
 * Created by wangxy on 16-6-21.
 */
object exp_d {

  def getNowTime = {
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    dateFormat.format(now)
  }

  def getNowDate(d: String) = {
    val d1 = d + "000"
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    dateFormat.format(d1.toLong)
  }

  /**
   * 获得给定时间的第n小时前的时间
   *
   * @param dateStr 给定时间 format(yyyy-MM-dd HH:mm:ss)
   * @param n       指定时间跨度
   * @return 返回给定时间的第n小时的时间
   */
  def getNhour(dateStr: String, n: Int = 0) = {
    val df: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val cal: Calendar = Calendar.getInstance()
    val dt = df.parse(dateStr)
    cal.setTime(dt)
    cal.add(Calendar.HOUR, n)
    cal.getTimeInMillis
  }

  object seg{
    def unapply(in: String): Option[Array[String]] = {
      val strArr = in.split(",", -1)
      // curdate, prov_id,city_id, area_id, lac, cell_id,user_id,carrier_id, net_type, exp_id, imei, time_type
      Some(Array(strArr(1),strArr(2),strArr(3),strArr(4),strArr(10),strArr(11),strArr(5),strArr(28),strArr(7),
        strArr(6),strArr(25),strArr(29)))
    }
  }

  def main(args: Array[String]): Unit = {

    val Array(inpath, stime, etime, outpath1, outpath2) = args

    val conf = new SparkConf().setAppName("exp_d")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(inpath)

    val time = getNowTime

    // 表 dim_dp.base_area_info
    val base_area_info = Map("1" -> "2")
    val areaInfo = sc.broadcast(base_area_info)

    // 表 dim_dp.mdm_data_dict
    val mdm_data_dict = Map("1" -> "2")
    val mdict = sc.broadcast(mdm_data_dict)

    // 表　sho_dp.h_cell_info
    val rdd1 = sc.parallelize(Seq(("1", "2")))

    val gRdd = rdd.map{
      case seg(Array(cur_date, prov_id, city_id, area_id, lac, cell_id, user_id, carrier_id, net_type, exp_id,
      imei, time_type)) =>
        // 需要关注的异常id
        val expids = List("2", "106", "107", "108", "110", "16", "105", "9", "14", "82", "116", "117", "111", "17",
          "18", "112")
        // 网络制式
        val  nettypes = List("1","5","7")
        // if中的条件　相当于sql中的where和join中的on
        val stimeStamp = getNhour(stime, -8)
        val etimeStamp = getNhour(etime, 16)

        if(nettypes.contains(net_type) && expids.contains(exp_id) && time_type == "1" && cur_date.toLong >= stimeStamp
          && cur_date.toLong <= etimeStamp){
          /*
              para_dp.p_int_general表中的内容　用于清洗数据中的net_type
              1	2	net_type	1	网络制式	2015-09-24 17:11:10
              5	3	net_type	1	网络制式	2015-09-24 17:11:10
              7	4	net_type	1	网络制式	2015-09-24 17:11:10
           */
          val nType = net_type match{
            case "1" => "2"
            case "5" => "3"
            case "7" => "4"
          }

          val prov_name = areaInfo.value.getOrElse(prov_id, "")
          val city_name = areaInfo.value.getOrElse(city_id, "")
          val area_name = areaInfo.value.getOrElse(area_id, "")
          val carrier_name = mdict.value.getOrElse(carrier_id, "")

          val key = Array(prov_id, city_id, lac, cell_id).mkString(",")
          val value = Array(prov_id, city_id, area_id, lac, cell_id, prov_name, city_name, area_name, user_id,
            imei, carrier_id, carrier_name, exp_id, time)

          (key, value)
        }else{
          ("", Array(""))
        }
    }.filter(_._1 != "")

    val res1 = gRdd.leftOuterJoin(rdd1).map{
      case (key, (Array(prov_id, city_id, area_id, lac, cell_id, prov_name, city_name, area_name, user_id,
      imei, carrier_id, carrier_name, exp_id, upd_date), Some(cell_name))) =>
        val key = (prov_id, city_id, area_id, lac, cell_id, cell_name, prov_name, city_name, area_name, user_id,
          imei, carrier_id, carrier_name, exp_id, upd_date)
        val value = 1
        (key, value)
      case (key, (Array(prov_id, city_id, area_id, lac, cell_id, prov_name, city_name, area_name, user_id,
      imei, carrier_id, carrier_name, exp_id, upd_date), None)) =>
        val key = (prov_id, city_id, area_id, lac, cell_id, "", prov_name, city_name, area_name, user_id,
          imei, carrier_id, carrier_name, exp_id, upd_date)
        val value = 1
        (key, value)
    }.reduceByKey(_+_).map{
      case ((prov_id, city_id, area_id, lac, cell_id, cell_name, prov_name, city_name, area_name, user_id,
      imei, carrier_id, carrier_name, exp_id, upd_date), cnt) =>
        Array(prov_id, city_id, area_id, lac, cell_id, cell_name, prov_name, city_name, area_name, user_id, imei,
          carrier_id, carrier_name, exp_id, cnt, upd_date, "1").mkString(",")
    }


  }

}
