package com.test

import java.util.HashMap

import scala.collection.JavaConversions.mapAsScalaMap
import scala.collection.mutable.Map
import scala.collection.mutable._

/**
 * Created by wangxy on 15-5-22.
 */
object test1 {
  def main(args: Array[String]): Unit ={

    val str1 = "fesfsefes,fesfsefs,fesfsefs"
    strCut(str1).foreach(println)

    makestr
    mkList
    myMap
  }

  def myMap:Unit={
    val score:Map[String, Int] = Map(("bob", 10),("lili", 9))

    val result = score.getOrElse("bob1",0)

    println("result="+result)
  }

  def strCut(x:String):Array[String]={
    val arr1 = new Array[String](3)
    val strB = new StringBuilder
    var index = 0
    for(i <- 0 to x.length -1) {
      if(','.equals(x(i))) {
        println("index = "+index)
        arr1.update(index, strB.toString())
        index += 1
        strB.clear()
      } else{
        strB.append(x(i))
      }
      arr1.update(index, strB.toString())
    }
    arr1
  }

  import scala.util.control.Breaks._
  def strCut1(x:String):(String,String)={
    val arr = new Array[String](2)
    var index = 0
    breakable{
      for(i <- 0 to x.length-1) {
        if ('|'.equals(x(i))) {
          //          index += 1
          index += 1
          if (3 == index) {
            arr.update(0, x.substring(0, i))
            arr.update(1, x.substring(i+1, x.length))
            break
          }
        }
      }
    }
    (arr(0).toString,arr(1).toString)
  }

  def makestr() {
    var map: Map[Int, String]= new HashMap[Int, String]

    map.put(1, "test1")
    map.put(2, "test2")
    map.put(3, "test3")
    println("makestr= "+map.mkString(","))
  }

  def mapTest {
    val list = List("a", "b", "c")
    val map: Map[String, String] = new HashMap[String, String]

    for (key <- list) {
      map(key) = map.getOrElse(key, "fk") + "e"
    }

    println(map.mkString(","))
  }

  def mkList: Unit ={
    val oldlist  = 2::(3::(4::Nil))
    val list = List(2,3,4)
    val list1 = 5::list
    val str1 = list1.mkString(":")

    println(str1)
  }

}
