package com.spark.myGraphx

import org.apache.spark.graphx._
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.mutable.ListBuffer

/**
 * Created by wangxy on 16-5-5.
 */
object MyPathTest1 {
  val start = "8"
  val end = "7"

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext("local", "test", conf)

    // 创建点 点属性(树深（初始为1），List(List(),List(),```每个路径))
    val vertex = sc.parallelize(Array(
      (3L,List(List("3"))),(7L,List(List("7"))),(5L,List(List("5"))),(2L,List(List("2"))), (6L,List(List("6")))
      , (8L,List(List("8"))), (1L,List(List("1")))
    ))

    // 创建边
    val edge = sc.parallelize(Array(
      Edge(3L, 7L, List("a")),Edge(2L, 5L, List("c")), Edge(5L, 7L, List("e")), Edge(2L, 7L, List("g")),
      Edge(3L, 5L, List("b")),Edge(6L, 5L, List("d")), Edge(8L, 1L,List("f")), Edge(8L, 2L, List("h"))
      , Edge(8L, 3L, List("i")), Edge(1L, 6L, List("i"))
    ))

    // 创建图
    val g = Graph(vertex, edge)

    // 更新点(点id, 点本身属性， sm或mm的结果)
    def vp(id: VertexId, attr: List[List[String]], message: List[List[String]]): List[List[String]] = {
      println(s"id=$id  attr=$attr   message=$message")

      // 首次不变
      if(message.length == 1 && message.head.head == "x"){
        attr
      } else{
        val oldStrList = attr.map(x=>x.slice(0,x.length-1).mkString(","))
        val newAttr = ListBuffer[List[String]]()
        newAttr ++= attr
        message.foreach{x =>
          val str = x.mkString(",")
          if(!oldStrList.contains(str)){
            val y = x :+ attr.head.head
            newAttr += y
          }
        }
        newAttr.toList
      }
    }

    // mapreduceTriplet中的map 发送的消息(子根id，点属性List(List()))
    def sm(triplet: EdgeTriplet[List[List[String]],List[String]]): Iterator[(VertexId,List[List[String]])] = {
      var Flag = false
      triplet.srcAttr.foreach{x =>
        if(x.contains(start))
          Flag = true
      }
      if(Flag){
        if(triplet.srcAttr.length == 1)
          Iterator((triplet.dstId, triplet.srcAttr))
        else
          Iterator((triplet.dstId, triplet.srcAttr.slice(1,triplet.srcAttr.length)))
      }
      else {
        Iterator.empty
      }
    }

    // mapreduceTriplet中的reduce   ps:同时到时会走mm
    def mm(v1: List[List[String]],v2: List[List[String]]): List[List[String]] = {
      println(s"!!!!!!  v1=$v1   v2=$v2")
      println(s"????? v1++v2=${v1++v2}")
      v1++v2
    }

    val kk = g.pregel(List(List("x")),Int.MaxValue,EdgeDirection.Out)(vp,sm,mm)

    println(kk.vertices.collect.mkString("\n"))

    sc.stop
  }
}
