package com.scalacode

/**
 * Created by wangxiaoyong on 2014/12/8.
 */

class Pair[T <: Comparable[T]](val first: T, val second: T){
  def smaller = if (first.compareTo(second) < 0) first else second
}

class Pair1[T <% Comparable[T]](val first: T, val second: T){
  def smaller = if (first.compareTo(second) < 0) first else second
}

object tools17{
  def makePair[T: Manifest](first: T, second: T): Array[T] ={
    val r = new Array[T](2)
    r(0) = first
    r(1) = second
    r
  }
}

class Pair2[T](val first: T, val second: T){
  def smaller(implicit ev: T <:< Ordered[T]) = {
    if(first < second) first else second
  }
}

object typeargs {
  def main(args: Array[String]): Unit ={
    val tmp1 = tools17.makePair(11, 12)
    for(v <- tmp1){
      println("v=======" + v)
    }

    val npair = new Pair("hi", "ha")
    var nsmaller = npair.smaller
    println("nsmaller=" + nsmaller)

    val npair1 = new Pair1(11, 12)
    val nsmaller1 = npair1.smaller
    println("nsmaller=" + nsmaller1)
  }

}
