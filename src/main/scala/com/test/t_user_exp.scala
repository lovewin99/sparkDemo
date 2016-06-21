package com.test

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import org.apache.spark.{SparkContext, SparkConf}

/**
 * t_user_exp.sql
 * Created by wangxy on 16-6-21.
 */
object t_user_exp {

  // stg_dp.s_basic_net_event
  object seg{
    def unapply(in: String): Option[Array[String]] = {
      val strArr = in.split(",", -1)
      //cur_date, prov_id, city_id, area_id, user_id, exp_id, net_type, lac, cell_id, map_longi, map_lati,time_type
      Some(Array(strArr(1), strArr(2),strArr(3),strArr(4),strArr(5), strArr(6),strArr(7), strArr(10), strArr(11),strArr(13), strArr(12), strArr(29)))
    }
  }

  def getNowTime = {
    val now: Date = new Date()
    val dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    dateFormat.format(now)
  }

  /**
   * 获得给定时间的第n小时前的时间
   *
   * @param dateStr 给定时间 format(yyyyMMddHHmmss)
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

  def main(args: Array[String]): Unit = {

//    val tabSegment = "log_id,cur_date,prov_id,city_id,area_id,user_id,exp_id,net_type,mcc,mnc,lac,cell_id,map_lati,map_longi,event_dura,cell_auid,grid_id,logic_cell_auid,exp_work_id,exp_work_status,loc_type,rxlev_rscp,exp_type,vsn,upd_date,imei,mob_system,gps_type,carrier_id,time_type,time_id"

    //s_basic_net_event

    if(args.length != 4){
      System.out.println("usage: <inpath> <start-time> <end-time> <outpath>")
      System.exit(1)
    }

    val Array(inpath, stime, etime, outpath) = args

    val conf = new SparkConf().setAppName("t_user_exp")
    val sc = new SparkContext(conf)

    val rdd = sc.textFile(inpath)

    val time = getNowTime

    val res = rdd.flatMap{
      case seg(Array(cur_date, prov_id, city_id, area_id, user_id, exp_id, net_type, lac, cell_id, map_longi, map_lati,time_type)) =>
        // 需要关注的异常id
        val expids = List(2, 106, 107, 108, 110, 16, 105, 9, 14, 82, 116, 117, 111, 17, 18, 112)
        // 网络制式
        val  nettypes = List(1,5,7)
        // if中的条件　相当于sql中的where和join中的on
        val stimeStamp = getNhour(stime, -8)
        val etimeStamp = getNhour(stime, 16)
        if(nettypes.contains(net_type) && expids.contains(exp_id) && time_type == "1" && cur_date.toLong >= stimeStamp && cur_date.toLong <= etimeStamp){
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
          Array(1,2,3,4).map{
            case 1 =>
              val grid_longi100 = f"${map_longi.toDouble}%.3f"
              val grid_lati100 = f"${map_lati.toDouble}%.3f"
              s"$prov_id,$city_id,$area_id,$user_id,$nType,1,NULL,NULL,1,$grid_longi100,$grid_lati100,$time"
            case 2 =>
              val tmp_longi = f"${map_longi.toDouble}%.3f".toDouble / 2
              val tmp_lati = f"${map_lati.toDouble}%.3f".toDouble / 2
              val grid_longi200 = f"$tmp_longi%.3f".toDouble * 2
              val grid_lati200  = f"$tmp_lati%.3f".toDouble * 2
              s"$prov_id,$city_id,$area_id,$user_id,$nType,1,NULL,NULL,2,$grid_longi200,$grid_lati200,$time"
            case 3 =>
              val tmp_longi = f"${map_longi.toDouble}%.3f".toDouble / 4
              val tmp_lati = f"${map_lati.toDouble}%.3f".toDouble / 4
              val grid_longi400 = f"$tmp_longi%.3f".toDouble * 4
              val grid_lati400  = f"$tmp_lati%.3f".toDouble * 4
              s"$prov_id,$city_id,$area_id,$user_id,$nType,1,NULL,NULL,4,$grid_longi400,$grid_lati400,$time"
            case 4 =>
              s"$prov_id,$city_id,$area_id,$user_id,$nType,2,$lac,$cell_id,NUll,NULL,NULL,$time"
          }
        }else{
          Array("")
        }
    }.distinct().filter(_ != "")

    res.saveAsTextFile(outpath)

    sc.stop()
  }

}
