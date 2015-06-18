package com.scalacode

import scala.collection.mutable

/**
 * Created by wangxiaoyong on 2014/12/11.
 */
// 单例模式 object类型做参数 直接用if判断类型
class Document{
  def setTitle(title: String)  : this.type = this
  def setAuthor(author: String) : this.type = this
}

class Book extends Document{
  def addChapter(chapter: String) : this.type = this

  import  scala.collection.mutable._
  type Index = HashMap[String, (Int, Int)] //类型别名
}

// 类型投影（外部类包含内部类实例）O#I
import scala.collection.mutable.ArrayBuffer
class Network{
  class Member(val name: String){
    val contact = new ArrayBuffer[Network#Member] //# 从属于任何Network对象
  }

  private val members = new ArrayBuffer[Member]

  def join(name: String) = {
    val m = new Member(name)
    members += m
    m
  }
}

// 自身类型
trait LogA{
  def log(msg: String)
}

class LogB{
  def voice(msg: String) = println("voice = " + msg)
}

trait LoggedException extends LogA{
  this: LogB =>                             //!!!!!!!!!!!!!!!!!!!!
    def haha(msg: String) = voice(msg)
}

// 抽象类型
trait Reader{
  type contents
  def read(filename: String): contents
}

class ImageReader extends Reader{
  type contents = String
  def read(filename: String) = "123"
}

object advancedtype18 {
  def main(args: Array[String]): Unit ={
    // 单例模式
    val book = new Book
    book.setTitle("123").addChapter("123")

    // 类型投影
    val net1 = new Network
    val net2 = new Network
    val mem1 = net1.join("mem1")
    val mem2 = net2.join("mem1")
    mem1.contact += mem2

    // 别名
    val hash = new book.Index
    hash.put("hello",(1,2));
    if(hash.contains("hello")){
      val test = hash.get("hello")
      println(test.get._2)
    }

    // 中置类型 ????
    type *[A, B] = (A, B)

    // 存在类型
    // Array[T] forSome { type T <: JComponet} ======= Array[_ <: JComponet]
  }

}
