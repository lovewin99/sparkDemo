package com.mllib

//import org.apache.spark.mllib.linalg.DenseMatrix

import breeze.linalg.{DenseVector, DenseMatrix, Matrix}
//import breeze.plot._
import breeze.linalg._
//import org.apache.spark.mllib
//import org.apache.spark.mllib.regression.LinearRegressionModel


/**
 * Created by wangxy on 16-5-10.
 */
object TestBasic extends App{

  // 矩阵乘法
//  val a = new DenseMatrix(2,3,Array(1.0,2.0,3.0,4.0,5.0,6.0))
//  val b = new DenseMatrix(3,2,Array(1.0,2.0,3.0,4.0,5.0,6.0))

//  def draw() = {
//    val f = Figure()
//    val p = f.subplot(0)
//    val x = linspace(0.0,1.0)
//
//    p += plot(x.toArray, x.toArray)
//    p += plot(x.toArray, x.toArray, '.')
//    p.xlabel = "x axis"
//    p.ylabel = "y axis"
//    f.saveas("lines.png")
//  }
////  val c = a.multiply(b)
//
//  def mat1() = {
//    val m = DenseMatrix.zeros[Int](5,5)
//    m(4,::) := DenseVector(1,2,3,4,5)
//    m(0 to 1, 0 to 1) := DenseMatrix((3,1),(-1,-2))
//    val a = m(::,0)
//    println(m)
//
//    // 计算行列式的值
//    val m1 = DenseMatrix.create(3,3,Array(1,2,3,1,1,1,1,4,5))
//    println(s"value = ${det(m1).toFloat}")
//
//    // 矩阵求逆 少转置
//    val m2 = inv(m1).t
//    println(s"m2=$m2")
//    val m3 = m2.map{x=>x.toFloat}
//    println(s"m3=$m3")
//  }
//  mat1()

  println(s"${}")

  // 转置
//  println(s"transpose = ${a.transpose}")



}
