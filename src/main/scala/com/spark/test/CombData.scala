package com.spark.test

/**
 * Created by wangxy on 16-2-29.
 */

import scala.io.Source

object CombData {

  def function1(): Unit = {
    val myData = Source.fromFile("/home/wangxy/data/jtest.txt").getLines().map{x =>
      val data = x.split(",", -1)
      val key = data(0)
      val time = data(1).replaceAll("[\\-: ]", "").slice(0,8)
      val value = time + "," + data.slice(2,data.length).mkString(",")
//      println(s"time=$time")
//      println(s"value=${value}")
      (key, value)
    }.toArray

    myData.foreach(println)

    val fstr = myData.toArray.groupBy(_._1).map{
      case (key, value) => {
        val tstr = value.map{x=>x._2}.mkString("|")
        val str = key + "," + tstr
        println(s"str=$str")
        str
      }
    }.mkString("\n")

    println(s"fstr= $fstr")
  }

  def function2(): Unit = {
    val myData = Source.fromFile("/home/wangxy/data/jtest.txt").getLines().map{x =>
      val data = x.split(",", -1)
      val time = data(1).replaceAll("[\\-: .]", "").slice(0,8)
      data.update(1, time)
      println(s"data=${data.mkString("|")}")
      data
    }
    val fstr = myData.toArray.groupBy(_(0)).map{x =>
      val k = x._1
      val v = x._2.map{x=>x.slice(1, x.length).mkString(",")}.mkString("|")
      k + "," + v
    }.mkString("\n")
    println(s"fstr1=$fstr")
  }

  def main(args: Array[String]): Unit ={

    function1()
//    function2()

  }

}
