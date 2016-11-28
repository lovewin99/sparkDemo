package com.spark.test

/**
 *
 * Created by wangxy on 16-11-22.
 */
object TestAccompany {

  object hello{
    var n = 1
    def haha(): Unit = {
      println("haha")
    }

    def newhaha: Unit = {
      println("yeah")
    }

    def apply(t: Int)={
      new hello(t)
    }
  }

  class hello(t: Int){
    def haha(): Unit = {
      hello.n = hello.n + t
      println(s"n=${hello.n}")
    }
  }

  def main(args: Array[String]): Unit = {

    hello.haha()
    val a = new hello(2)
    val b = new hello(3)
    val c = hello(1)
    a.haha()
    b.haha()
    c.haha()

  }

}
