package com.mllib

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.HashPartitioner

/**
 * Created by wangxy on 15-12-3.
 */
object PageRank {

  def main(args: Array[String]): Unit = {
//    val srcpath = "/home/wangxy/data/pagerank.txt"
//    val tarpath = "/home/wangxy/data/pagerankres.txt"
    val srcpath = args(0)
    val tarpath = args(1)
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
//    val links = sc.objectFile[(String, Seq[String])](srcpath)
//      .partitionBy(new HashPartitioner(10))
//      .persist()

    val links = sc.textFile(srcpath).map{l =>
      val strArr = l.split(",", -1)
      (strArr(0), strArr.slice(1,100))
    }

    //设置页面的初始rank值为1.0
    var ranks = links.mapValues(_ => 1.0)

    //迭代10次
    for (i <- 0 until 10) {
      val contributions = links.join(ranks).flatMap {
        case (pageId, (links, rank)) =>
          //注意此时的links为模式匹配获得的值，类型为Seq[String]，并非前面读取出来的页面List
          links.map(dest => (dest, rank / links.size))
      }
      //简化了的rank计算公式
      ranks = contributions.reduceByKey(_ + _).mapValues(0.15 + 0.85 * _)
    }
    ranks.saveAsTextFile(tarpath)
  }

}
