package com.test

/**
 * Created by wangxy on 15-9-29.
 */
object addpartition {
  val str1 = "alter table s_basic_para_detail add partition(time_type='1',time_id='2015-09-$1') location '/data/stg_dp/s_basic_para_detail/time_type=1/2015-09-$1';"

  val str2= "alter table s_basic_para_detail drop partition(time_type='1',time_id='2015-09-$1');"

  val date1 = List[String]("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31")

  def main(args: Array[String]): Unit = {

    date1.foreach(x => {
      println(str1.replace("$1", x))
    })
  }
}
