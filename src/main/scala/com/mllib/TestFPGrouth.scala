package com.mllib

import org.apache.spark.{SparkContext, SparkConf}
import org.apache.spark.mllib.fpm.FPGrowth

/**
 * Created by wangxy on 16-5-3.
 */
object TestFPGrouth {


  def main(args: Array[String]): Unit = {
    val data_path = "/home/tmp/sample_fpgrowth.txt"

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val data = sc.textFile(data_path)

    val examples = data.map(_.split(" ")).cache()

    //建立模型

    val minSupport = 2

    val numPartition = 10

    val model = new FPGrowth().setMinSupport(minSupport).setNumPartitions(numPartition).run(examples)

    //打印结果

    println(s"Number of frequent itemsets: ${model.freqItemsets.count()}")

    model.freqItemsets.collect().foreach { itemset =>
      println(itemset.items.mkString("[", ",", "]") + ", " + itemset.freq)
    }
  }
}
