package com.scalacode

/**
 * Created by wangxiaoyong on 2014/12/5.
 */

//特质
trait tra{
  def voice(msg : String) = println(msg)
  def test = {}
}

class tra2 extends tra{
  def voice(count : Int) ={
    if(count > 2){
      println(">2")
    }else{
      println("<=2")
    }
  }
}

trait tra3 extends tra{
  override def test = println("tra3 test")
}

trait tra4 extends tra{
  override def test = println("tra4 test")
}

//从混合中派生
class htest{
  def start = println("test start")
}

trait htest1 extends htest with tra3 {

}

class htest2 extends htest1{

}

object trait10 {
  def main(args: Array[String]): Unit ={
    //特质
    val otra = new tra2 with tra4 with tra3
    otra.voice("2")
    otra.test

    val ttra = new htest2

    ttra.start
    ttra.test
    ttra.voice("!!!!!!!!!!!")
  }
}
