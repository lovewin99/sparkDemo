package com.spark.myGraphx

import org.apache.spark.graphx._
import org.apache.spark.{SparkContext, SparkConf}

import scala.collection.mutable.ListBuffer

/**
 * 使用道路拓扑生成全路径
 * Created by wangxy on 16-5-11.
 */
object SHPath {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext("local", "SHPath", conf)

    val strRdd = sc.textFile("/home/wangxy/data/").map(_.split(",", -1)).cache()

    // 构建点
    val vertex = strRdd.groupBy(_(0)).map{
      case (id, x) => (id.toLong, List((List(),List())))
      case _ => (0L, List((List(),List())))
    }

    // 构建边
    val edge = strRdd.groupBy(_(2)).map{
      case (line, iter) => {
        val lacciSeq = iter.toArray.sortBy(_(5)).map{x=>x(3)+"-"+x(4)}.mkString("|")
        val srcV = iter.head(0)
        val dstV = iter.head(1)
        Edge(srcV.toLong, dstV.toLong, lacciSeq)
      }
    }

    // 构建图
    val g = Graph(vertex, edge)

    // 更新点(点id, 点本身属性， sm或mm的结果)
    def vp(id: VertexId, attr: List[(List[String], List[String])], message: List[(List[String],List[String])]): List[(List[String], List[String])] = {
      if(message.length == 1 && message.head._1.head == ""){
        // 程序首次更新 什么都不做
        attr
      }else{
        message.map{
          case (vertexList, edgeList) => {
            val v = vertexList :+ id.toString
            (v, edgeList)
          }
        }
      }
    }

    sc.stop()
  }

}
