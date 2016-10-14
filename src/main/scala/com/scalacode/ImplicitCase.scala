package com.scalacode

/**
 *
 * Created by wangxy on 16-10-12.
 */

class Insert {
  def kankan() = println("kankan")

  //def add(s: String) = println(s"llll+++$s")
}

class InsertA(insert : Insert){

  def add(msg : String){
    println("msg:"+msg)
  }

}

object ImplicitCase extends App{

  // 对象隐式转换 用于添加方法
//  implicit def kkk(insert : Insert) = new InsertA(insert)
//  val test = new Insert
//  test.kankan()
//  test.add("test")
  //------------------------------------------------------

  // 柯里化 参数隐式转换 如果有明确参数 则使用传入参数 如果没有
//  def testParam(str: String)(implicit name: String){
//    println("kankan:"+str+"::"+name)
//  }
//
//  implicit val name1 = "test"
//
//
//  testParam("hehe")
//  testParam("aaa")("bbbk")
//
//  def test1(implicit str1: Int){
//    println(str1)
//  }
//  implicit val str1 = 1111
//  test1


  //-------------------------------------------------------
  // 隐式转换 添加方法
//  implicit class Train(a: Int){
//    def add(b: Int) : Int  = a+b
//  }
//
//  println(2.add(3))

  //-------------------------------------------------------
  //基础隐式转换
  def func1(n: String): Unit = {
    println(n)
  }
  implicit def xxx(n: Int) = n.toString
  func1(123)


}
