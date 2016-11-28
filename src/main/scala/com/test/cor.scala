package com.test

/**
 * Created by wangxy on 16-7-6.
 */

import org.apache.spark.mllib
import scala.collection.mutable.ArrayBuffer
import scala.math._

object cor {

  def cor(a: List[Double], b: List[Double]) = {
    val fz = a.zip(b).map(x=>x._1*x._2).sum
    val fm = sqrt(a.map(pow(_, 2.0)).sum)*sqrt(b.map(pow(_, 2.0)).sum)
    fz / fm
  }

  // 计算方差
  def getVariance(inputData: Array[Double]): Double = {
    val average = inputData./:(0.0)(_+_) / inputData.length
    var result = 0.0
    inputData.foreach{x =>
      result += pow(x-average, 2)
    }
    // 原来就是长度减1
    val res = result / (inputData.length - 1)
    res
  }

  // 计算相似系数
  def getCorrcoef(finger: Array[Double], scandata: Array[Double]): Double ={
    val averageT = scandata./:(0.0)(_+_) / scandata.length
    val averageL = finger./:(0.0)(_+_) / finger.length
    val cov = ArrayBuffer[Double]()
    for(i <- 0 to (scandata.length - 1)){
      cov += (scandata(i) - averageT) * (finger(i) -averageL)
    }
    var deviation = sqrt(getVariance(scandata)) * sqrt(getVariance(finger))
    if(deviation < 0.0000001)
      deviation = 0.0001
    val res = cov./:(0.0)(_+_) / (cov.length-1) / deviation
    res
  }

  def variance(i: Array[Double]):Double = {
    val avr = i.sum / i.length
    i.map{x=>pow(x-avr,2)}.sum / (i.length - 1)
  }

  def corrcoef(a: Array[Double], b: Array[Double]): Double = {
    val aT = a.sum / a.length
    val bT = b.sum / b.length
    println(s"aT=$aT")
    println(s"bT=$bT")
    val cov = a.zip(b).map(x=> (x._1-aT)*(x._2-bT)).sum
    println(s"cov=$cov")
    var dev = sqrt(variance(a)) * sqrt(variance(b))
    println(s"dev=$dev")
    println(s"d1=${variance(a)}")
    println(s"d2=${variance(b)}")
    if(dev < 0.0000001)
      dev = 0.0001
    cov / (a.length-1) / dev
  }

  def main(args: Array[String]): Unit = {
    println(cor(List(1.0f,2.0f), List(2.0f,3.0f)))
//    println(corrcoef(Array(2.0f,-15.0f), Array(2.0f,7.0f)))
//    println(getCorrcoef(Array(2.0f,15.0f), Array(2.0f,3.0f)))
  }

}
