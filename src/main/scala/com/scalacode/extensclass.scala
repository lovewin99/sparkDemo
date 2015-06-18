package com.scalacode

import scala.beans.BeanProperty

/**
 * Created by wangxiaoyong on 2014/12/5.
 */

class testapply(@BeanProperty var index: Int, @BeanProperty var name: String){

}

// 伴生类和apply
object testapply{
  private[this] var index = 0
  def apply(str: String) = {
    index += 1
    new testapply(index, str)
  }

}

class person{
  val name = "bob"
  override def toString = getClass.getName + "[name=" + name + "]"
}

class employee extends person{
  val salary = 1.0f
  override def toString = getClass.getName + "[salary=" + salary + "]"
}

class animal{
  val range = 10
  var env : Array[Int] = new Array[Int](range)
}

class ant extends {
  override val range = 2
} with animal{
  def voice = println("haha!!!")
}

object extensclass {
  def main(args: Array[String]){
    // 类
        val op : person = new employee
        println(op.toString)

        val alien = new person() {
          def greeting = "Greetings"
        }

        val ent1 = new ant
        println("length="+ent1.env.length)
        ent1.voice

        println("alien ="+ alien.greeting)
    // 匹配 一个一个顺序试
        op match{
          case s : employee => {
            println("case employee " + s.toString)
          }
          case s : person => {
            println("case person " + s.toString)
          }
          case _ =>{
            println("case default ")
          }
        }
        if (op.isInstanceOf[employee]){
          println(op.toString)
        }

        // 伴生 apply
        val objtest1 = testapply("test1")
        println("index="+objtest1.index+"    name="+objtest1.name)
        val objtest2 = testapply("test2")
        println("index="+objtest2.index+"    name="+objtest2.name)
  }
}
