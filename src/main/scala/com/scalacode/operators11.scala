package com.scalacode

import scala.beans.BeanProperty

/**
 * Created by wangxiaoyong on 2014/12/5.
 */
//操作符
class Fraction(@BeanProperty var n1: Int, @BeanProperty var d1: Int){
  private val num = 2
  private val den = 3
  def *(other : Fraction) = new Fraction(num * other.num, den * other.den)
}

//update函数(一个参数（），最后一个参数=)
class testUpdate{
  def update(arg1:String, arg2:String)={
    println(arg1+"|"+arg2)
    "hello update"
  }
}

//unapply函数（参数=，返回值在括号）分解 apply构造
class testunapply{
  def unapply(input:Fraction) = {
    Some(input.n1, input.d1)
  }
}

object IsCompoud {
  def unapply(input: Int) = {
    if (input == 3) {
      true
    } else {
      false
    }
  }
}

object operators11 {
  def main(args: Array[String]){
    //操作符
        val test1 = new Fraction(2,2)
        val test2 = new Fraction(3,3)
        val test3 :Fraction = test1 * test2
        println("tes3.n="+test3.n1+"     test3.d1="+test3.d1)

    //update函数
        val tUp = new testUpdate
        val str = tUp("hello") = "world"
        println("str =" + str)

    //unapply函数
        val tUn = new testunapply
        val tDate = new Fraction(3,3)
        val tUn(arg1, arg2) = tDate
        println("arg1="+ arg1+"  arg2="+arg2)
        tDate match{
          case tUn(arg1 @ IsCompoud(), arg2 @ IsCompoud())=>{
            println("11111111")
          }
          case _ =>{
            println("??????????")
          }
        }
  }
}
