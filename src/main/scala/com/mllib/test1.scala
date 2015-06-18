package com.mllib

import org.apache.spark.mllib.linalg.distributed.{IndexedRowMatrix, IndexedRow, RowMatrix}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.{Vector, Vectors, Matrix, Matrices}
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.rdd.RDD

import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}

/**
 * Created by wangxy on 15-6-9.
 */

object sparkInstance{
  private val conf = new SparkConf().setAppName("vector/matrix-base Application")
  private val sc = new SparkContext(conf)
  def getInstance: SparkContext = sc
}

class tvec{

  def test(): Unit={
    //普通 稠密稀疏向量定义
    val dv: Vector = Vectors.dense(1.0, 0.0, 3.0)
    val sv1: Vector = Vectors.sparse(3, Array(0,2), Array(1.0, 3.0))
    val sv2: Vector = Vectors.sparse(3, Seq((0, 1.0), (2, 3.0)))

    // 标记向量
    val pos = LabeledPoint(1.0, Vectors.dense(1.0, 0.0, 3.0))
    val neg = LabeledPoint(0.0, Vectors.sparse(3, Array(0, 2), Array(1.0, 3.0)))

    println("dv="+dv.toString)
    println("dv="+sv1.toString)
    println("pos="+pos.toString())
  }

  //读svm文件
  def readvec(filepath: String): Unit={
    val example: RDD[LabeledPoint] = MLUtils.loadLibSVMFile(sparkInstance.getInstance, filepath)
    val res = example.collect()
    for(i <- 0 to res.length-1){
      println(res(i))
    }
  }
}

class tmetr{
  def test: Unit ={

    // 普通矩阵
    val dm: Matrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))
    println("Matrix="+dm.toString())

    // 行矩阵
    val rows: RDD[Vector] = sparkInstance.getInstance.parallelize(Seq(Vectors.dense(1.0, 2.0, 3.0), Vectors.dense(1.0, 2.0, 3.0)))
    val mat: RowMatrix = new RowMatrix(rows)
    println("row="+mat.numRows()+"    col="+mat.numCols())

    // 行索引矩阵
    val indexrows: RDD[IndexedRow] = sparkInstance.getInstance.parallelize(Seq(IndexedRow(1:Long, Vectors.dense(1.0, 2.0, 3.0))))
    val indexmat: IndexedRowMatrix = new IndexedRowMatrix(indexrows)

  }
}

class tstatic{
  def test: Unit ={
    val rows: RDD[Vector] = sparkInstance.getInstance.parallelize(Seq(Vectors.dense(1.0, 0.0, 3.0), Vectors.dense(1.0, 2.0, 3.0)))

    val summary: MultivariateStatisticalSummary = Statistics.colStats(rows)
    println("mean value="+summary.mean) // a dense vector containing the mean value for each column
    println("column-wise variance="+summary.variance) // column-wise variance
    println("nonzeros="+summary.numNonzeros) // number of nonzeros in each column
  }
}

object test1 {

  def main(args: Array[String]) : Unit = {
/*    if (1 != args.length){
      System.err.println("args: <filepath>")
      System.exit(1)
    }

    val tvec1 = new tvec
    tvec1.readvec(args(0))
    tvec1.test

    val tmetr = new tmetr
    tmetr.test
*/
    val tstat = new tstatic
    tstat.test
  }
}
