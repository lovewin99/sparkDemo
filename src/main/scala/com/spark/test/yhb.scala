package com.spark.test

import com.mllib.MyDbscan1
import org.apache.spark.{SparkContext, SparkConf}

/**
 *spark-submit --master spark://cloud138:7077 --total-executor-cores 50 --executor-memory 30g \
--conf "spark.executor.extraJavaOptions=-Xss10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:-CMSConcurrentMTEnabled \
-XX:CMSInitiatingOccupancyFraction=60 -XX:+CMSParallelRemarkEnabled" --class com.spark.test.yhb sparkdemo_2.10-1.0.jar \
lisp/datafilter wxy/db1 50 100
 * Created by wangxy on 16-7-18.
 */
object yhb {

  /**
   * Created by s
   * 2016-7-4.
   * spark-submit --master spark://cloud138:7077 --executor-cores 50 --executor-memory 10g --class com.spark.test.yhb sparkdemo_2.10-1.0.jar lisp/datafilter wxy/db1 50 100
0	  LOG_ID
1	  TS_USER_AUID
2	  TIME_BEGIN
3	  TIME_END
4	  PROVINCE_ID
5	  CITY_ID
6	  AREA_ID
7	  MAP_LATITUDE
8	  MAP_LONGITUDE
9	  MNC
10	MCC
11	LAC
12	CELL_ID
13	NET_TYPE
14	PARA_VALUE
15	SOFT_VER
16	IMEI
17	UPD_TIME
18	GPS_TYPE
   * 316218642,186004
haibao.yu(于海宝) 07-18 15:12:30
58927,1466590978,1466590978,1,103,10310,40.026844,116.425446,1,460,41204,60421,5,-65,4.3.123,865800029729420,2016-06-23 08:02:49.0,2,	3
   316218642,18600458927,1466590983,1466590983,1,103,10310,40.026844,116.425446,1,460,41204,60423,5,-65,4.3.123,865800029729420,2016-06-23 08:02:49.0,2,	3
   */
    def main(args: Array[String]) {
      val conf = new SparkConf().setAppName("DBScan filter")
      val sc = new SparkContext(conf)
      sc.setLogLevel("WARN")

      val Array(input, output, eps, minPts) = args

      val rdd = sc.textFile(input, 30)
      rdd.map{ x
        =>
        val arr = x.split(",", 14)
        ((arr(11), arr(12)), ((arr(8).toDouble, arr(7).toDouble), x))
      }.groupByKey().filter(_._1 != ("-1","-1")).map{
        case(k, v) =>
          val list = v.toList
          val dbList = list.map(_._1).distinct

          val validateMap = MyDbscan1.dbscanMap(dbList, eps.toDouble, minPts.toInt)
          validateMap
        //        v.map{
        //          case((lat, lon), x) =>
        //          validateMap.getOrElse((lon, lat), None) match {
        //            case None => None
        //            case _ => x
        //          }
        //        }
      }.filter(_ != None).saveAsTextFile(output)
    }


}
