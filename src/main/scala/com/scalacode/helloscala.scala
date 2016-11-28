package com.scalacode

/**
 * Created by wangxiaoyong on 2014/11/28.
 */
//object test{
//  def modify(test: Int)={
//    var test1 = test
//    test1 = 100
//    test1
//  }
//}

class test1{
  def modify(test: Int): Unit ={
    var test1 = test
    test1 = 100
  }
}

object helloscala {

  def main(args:Array[String]) {
    println("hello scala")


    var na = 1
    na match{
      case 1 =>{
        println("run!!!")
      }
      case _ =>{
        println("run default!!!")
      }
    }

    // 映射 元组
    val score = Map("alice"->10, "bob"->20, "cindy"->30)
        val score1 =for((k,v) <- score)  yield {
          println("name="+k+"  score="+v)
          (v, k)
        }

    for(v <- score1.values) {
        println("score1="+v)
     }

    val n2: BigInt = 2
    val num123 = n2.modPow(2, 1024)
    println("num123=" + num123)

//    // 函数参数
//    var test1 = 1
//    test.modify(test1)
//    println("test1 ="+ test1)
  }
}
