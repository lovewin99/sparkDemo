package com.spark.myGraphx

import org.apache.spark.graphx._
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by wangxy on 16-5-4.
 */


object CaculatePath {

  def main(args: Array[String]): Unit = {

    if(args.length != 4){
      System.out.println("Usage: <in-file> <out-file> <start> <end>")
      System.exit(1)
    }

    val Array(inPath, outPath, start, end) = args

    val conf = new SparkConf()
    val sc = new SparkContext("local", "test", conf)

    // 边的数据结构，边属性是小区序列
    val edgeRdd = sc.textFile(inPath).map{x =>
      val strArr = x.split(",", -1)
      val flag = strArr(3)+"-"+strArr(4)
      ((strArr.head, strArr(1)), flag)
    }.reduceByKey((x,y)=>x+"|"+y).map{case((srcVid, dstVid), arr) => Edge(srcVid.toLong, dstVid.toLong, arr)}.cache()

    // 点的数据结构第一个list放点的序列，第二个放边信息的序列
    val vertexRdd = sc.textFile(inPath).map{x=>(x.split(",", -1).head.toLong, List(List(),List()))}.distinct().cache()

    // 创建图
    val g = Graph(vertexRdd, edgeRdd)

    // 更新点(点id, 点本身属性， sm或mm的结果)
    def vp(id: VertexId, attr: List[String], message: List[String]): List[String] = {
      //      println("attr:" + attr)
      //      println("message:" + message)
      println(s"id=$id   message=$message")
      if (message.length == 1 && message.head == "x") {
        attr
      } else {
        message ++ attr
      }
    }

    // mapreduceTriplet中的map
    def sm(triple: EdgeTriplet[List[String],List[String]]): Iterator[(VertexId,List[String])] = {
      //      println("srcAttr:"+triple.srcAttr+";dstAttr:"+triple.dstAttr+";edge attr:"+triple.attr)
      //println("sm:"+List(triple.srcId.toString):+triple.dstId.toString)
      if(triple.srcAttr.head.toString.contains(start) || triple.srcAttr.head.toString.contains("3") || triple.srcAttr.head.toString.contains("6")){
        Iterator((triple.dstId, triple.srcAttr))
      } else {
        Iterator.empty
      }
    }

    // mapreduceTriplet中的reduce   ps:同时到时会走mm
    def mm(v1: List[String],v2: List[String]): List[String] = {
      println(s"!!!!!!  v1=${v1.mkString(",")}   v2=${v2.mkString(",")}")
      v1++v2
    }

//    val kk = g.pregel(List("x"),Int.MaxValue,EdgeDirection.Out)(vp,sm,mm)

//    println(kk.vertices.collect.mkString("\n"))

    sc.stop
  }
}
