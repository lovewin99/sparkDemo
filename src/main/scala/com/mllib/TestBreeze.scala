package com.mllib

//import breeze.linalg._
//import breeze.plot._

//import org.apache.spark.mllib.linalg.DenseVector


/**
 * Created by wangxy on 16-5-10.
 */
object TestBreeze {

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

  def vecMat() = {

//    val m1 = DenseMatrix.create(3,3,Array(1,2,3,1,1,1,1,4,5))
//    println(s"value = ${det(m1).toFloat}")

    // 矩阵求逆 少转置
//    val m2 = inv(m1).t
//    println(s"m2=$m2")
//    val m3 = m2.map{x=>x.toFloat}
//    println(s"m3=$m3")

//    x(1) = 2
//    println(s"x2=$x")
//    x(3 to 4) := .5
//    println(s"x3=$x")
//    x(0 to 1) := DenseVector(.1,.2)
//    println(s"x4=$x")
  }


  def main(args: Array[String]): Unit = {
    vecMat()
  }

}
