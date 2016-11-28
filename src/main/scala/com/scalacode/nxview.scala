package com.scalacode

/**
 * Created by wangxy on 15-11-17.
 */
object nxview extends App{
  //协变
  class Contain[+A]
  val con: Contain[AnyRef] = new Contain[String]

  //上界
  type T = {
    def get(): Unit
    def close(): Unit
  }

  def withCon[A<:T, B](h: A)(f: A=>B): B = {
    try{
      h.get
      f(h)
    } finally {
      h.close
    }
  }

  class Con{
    def get() = println("get !!")
    def close() = println("close !!")
  }

  val con1 = new Con

  withCon(con1)(Con => println("doing!!!"))

  // 视界
  def withCon1[A <% T, B](h: A)(f: A=>B): B = {
    try{
      h.get
      f(h)
    } finally {
      h.close
    }
  }

  class kankan{
    def get() = println("get1!!!!")
  }

  class RichKan(c: kankan){
    def get() = c.get()
    def close() = println("close1!!!!")
  }

  implicit def xxx(c: kankan) = new RichKan(c)

  val k = new kankan

  withCon1(k)(k => println("ouyea!!!"))


//  def main(): Unit = {
//    val con1 = new Con
//  }

}
