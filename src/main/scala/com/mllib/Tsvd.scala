package com.mllib

/**
 * Created by wangxy on 16-3-22.
 */

import org.apache.spark.mllib.linalg.{Vectors, Vector, Matrix, SingularValueDecomposition}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

object Tsvd extends App{

  // 行矩阵
  val rows: RDD[Vector] = sparkInstance.getInstance.parallelize(Seq(Vectors.dense(1.0, 2.0, 3.0), Vectors.dense(2.0, 2.0, 3.0), Vectors.dense(3.0,2.0,4.0)))
  val mat: RowMatrix = new RowMatrix(rows)

  // Compute the top 20 singular values and corresponding singular vectors.
  val svd: SingularValueDecomposition[RowMatrix, Matrix] = mat.computeSVD(3, computeU = true)
  val U: RowMatrix = svd.U // The U factor is a RowMatrix.
  val s: Vector = svd.s // The singular values are stored in a local dense vector.
  val V: Matrix = svd.V // The V factor is a local dense matrix.

//  U.rows
}
