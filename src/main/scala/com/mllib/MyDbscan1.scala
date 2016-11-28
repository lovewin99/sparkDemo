package com.mllib

import scala.math._
import scala.collection.mutable.{Map => mMap, ListBuffer}

/**
 *
 * Created by wangxy on 16-7-18.
 */
object MyDbscan1 {
  // 计算距离函数
  def distance(p1: (Double, Double), p2: (Double, Double)): Double = {
    sqrt(pow(p1._1 - p2._1, 2) + pow(p1._2 - p2._2, 2))
  }

  // 深度优先遍历树
  def mergeCluster(m: mMap[(Double, Double), List[(Double, Double)]], p: (Double, Double), minPts: Int): Set[(Double, Double)] = {
    m.get(p) match{
      case Some(x) if x.length > minPts =>
        // 在map中删除邻居数大于minPts的点 防止重复遍历
        m -= p
        // 递归方法深度优先遍历树
        x.flatMap{y =>
          mergeCluster(m, y, minPts)
        }.toSet ++ Set(p)
      case _ => Set(p)
    }
  }

  /**
   * dascan算法
   * @param l 位置信息链表
   * @param eps 半径
   * @param minPts 半径内最小邻居数量
   * @return 基于密度的族群
   */

  def dbscan(l: List[(Double, Double)], eps: Double, minPts: Int): List[List[(Double, Double)]] = {

    // 计算每个节点的邻居 生成map key:点坐标 value：List[邻居点坐标]
    val a = l.map{x =>
      val b = l.map{y =>
        if(x != y && distance(x, y) <= eps)
          y
        else
          (-1.0,-1.0)
      }.filter(_ != (-1.0,-1.0)).distinct
      (x, b)
    }.toMap

    // 可变map转不可变map 以方便删除操作
    val v = mMap[(Double,Double), List[(Double, Double)]]()
    v ++= a

    // 计算族群
    l.map{x =>
      if(v.getOrElse(x, List()).size > minPts)
        mergeCluster(v, x, minPts).toList
      else
        List()
    }.filter(_.nonEmpty)
  }

  def dbscanMap(l: List[(Double, Double)], eps: Double, minPts: Int): Map[(Double, Double), String] = {
    dbscan(l, eps, minPts).flatMap{x =>
      x.map((_, null))
    }.toMap
  }


  def main(args: Array[String]): Unit = {

    val a = List((1.0,1.0),(1.1,1.1),(1.2,1.2),(1.3,1.3),(1.4,1.4),(1.5,1.5),(10.0,10.0),(10.1,10.1),(10.2,10.2),(10.3,10.3),(10.4,10.4),(10.5,10.5),(5.0,5.0),(5.1,5.1))

    dbscan(a, 2, 4).foreach(println)
  }
}
