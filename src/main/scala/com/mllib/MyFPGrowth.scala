package com.mllib

/**
 * Created by wangxy on 16-5-9.
 */

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
import org.apache.spark.mllib.fpm.AssociationRules
import org.apache.spark.mllib.fpm.FPGrowth.FreqItemset

object MyFPGrowth {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext("local", "test", conf)

//    val data = sc.textFile("data/mllib/sample_fpgrowth.txt")

//    r z h k p
//    z y x w v u t s
//    s x o n r
//    x z y m t s q e
//    z
//    x z y r q t p

    val data = sc.parallelize(Seq(
      "r z h k p", "z y x w v u t s", "s x o n r", "x z y m t s q e", "z", "x z y r q t p"
    ))

    val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))

    val fpg = new FPGrowth()
      .setMinSupport(0.2)
      .setNumPartitions(10)
    val model = fpg.run(transactions)

    model.freqItemsets.collect().foreach { itemset =>
      println(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq)
    }

    val minConfidence = 0.8
    model.generateAssociationRules(minConfidence).collect().foreach { rule =>
      println(
        rule.antecedent.mkString("[", ",", "]")
          + " => " + rule.consequent .mkString("[", ",", "]")
          + ", " + rule.confidence)
    }

  }

}
