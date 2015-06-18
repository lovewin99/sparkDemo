package com.scalacode

/**
 * Created by wangxiaoyong on 2014/12/8.
 */

object tools{
  // set
  def digits(n: Int): Set[Int]={
    if(n < 0) digits(-n)
    else if(n < 10) Set(n)
    else digits(n / 10) + n % 10
  }

  //list
  def sum(nTmp: List[Int]): Int ={
    nTmp match{
      case Nil => 0
      case h :: t => h + sum(t)
    }
  }

  def deletetwo(nTmp: scala.collection.mutable.LinkedList[Int]) = {
    var nTmp1 = nTmp
    while(nTmp1 != Nil && nTmp1.next != Nil){
      nTmp1.next = nTmp1.next.next;
      nTmp1 = nTmp1.next
    }
  }

  //流
  def numsFrom(n :Int): Stream[Int] = n #:: numsFrom(n+1)

}

object set13 {
  def main(args: Array[String]): Unit ={
    // set
    val n1 = 123456
    val setn = tools.digits(n1)
    val setn1 = setn + 9
    for(nTmp <- setn1){
//      println("setn="+ nTmp)
    }

    // list
    val digits1 = List(4, 2)
    val digits2 = 9 :: digits1
    for (nTmp <- digits2){
//      println("nTmp =" + nTmp)
    }

    val nsum = tools.sum(digits2)
    println("nsum ="+ nsum)

    // linkedlist
    val vList = scala.collection.mutable.LinkedList(1, -2, 7, -9)
//    tools.deletetwo(vList)
//    for(nTmp <- vList){
//      println("list = "+ nTmp)
//    }

    // 折叠
    val num = vList.reduceLeft(_ + _)
    println("num =" + num)

    // 拉链
    val tTmp1 = List(1, 2, 3)
    val tTmp2 = List(10, 20, 30)
    val tTmp = tTmp1 zip tTmp2 map {p => p._1 * p._2}
    for(v <- tTmp) {
//      println("v =" + v)
    }

    // 流
    val nStream = tools.numsFrom(2).map(x => x*x)
    val nStream1 = nStream.take(3).force
    for(v <- nStream1){
//      println("v =" + v)
    }

    // 懒视图
    val nTest = vList.view.map(x => x + 100)
    println("nTest(3)=" + nTest(3))
  }
}
