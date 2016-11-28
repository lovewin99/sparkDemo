package com.scalacode

import math.sqrt
/**
 * Created by wangxiaoyong on 2014/12/5.
 */

//作为参数的函数
class tArgFun{
  def voice(v:String) = println("haha !!" + v)
  //函数做参数
  def funcasargs(x: (Double) => Double, y: Double) = x(y)
  //返回函数
  def mulby(factor: Double) = (x: Double) => factor * x
  //柯里化
  def mulby1(factor: Double)(x: Double) = factor * x
}

object function12 {
  def main(args:Array[String]){
    //测试值的函数
    val ofunc = new tArgFun
    val fun = ofunc.voice _
    fun("myFunc")
    //返回函数
    val mub1 = ofunc.mulby(2)
    println("test = " + mub1(3))
    //函数做参数
    var value1 = ofunc.funcasargs(sqrt _, 0.04)
    println("test2 =" + value1)
    value1 = ofunc.funcasargs(x => x * 200, 0.04)
    println("test3 =" + value1)

    val func1: (Double) => Double = 3 * _
    value1 = func1(6)
    println("test4 =" + value1)

  }
}
