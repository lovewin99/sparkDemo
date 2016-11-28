package com.scalacode

/**
 * Created by wangxiaoyong on 2014/12/8.
 */

//样例类(绑定打折) (加sealed为密封类)
sealed abstract class Item
case class Article(description: String, price: Double) extends Item
case class Bundle(description: String, discount: Double, items: Item*) extends Item

// 样例类加sealed 可以模拟枚举
sealed abstract class color
case object red extends color
case object blue extends color
case object yellow extends color

object tools1{
  def price(it: Item): Double = it match{
    case Article(_, p) => {
      println("p1=" + p)
      p
    }
    case Bundle(_, disc, its @ _*) =>{
      var sum = its.map(price _).sum
      println("sum =" + sum)
      sum - disc
    }
  }

  // 可变参数
  def sum1(a:String, itr: Int*): Int ={
//    val itr1 = itr.iterator
    var tmpsum = 0;
//    while(itr1.hasNext){
//      tmpsum += itr1.next
//    }
    for(n <- itr){
      tmpsum += n
    }
    tmpsum
  }
}

object modematch14 {
  def main(args: Array[String]): Unit ={

    //使用守卫
    val test1 = Array((1,2), (1,3), (1,4), (2, 5))
    for ((k, v) <- test1 if k == 1){
      println("v =" + v)
    }

    val test2 = Bundle("1", 20.0, Article("2", 40.0), Bundle("3", 10.0, Article("4", 80.0), Article("5", 50.0)))
    val nTmp = tools1.price(test2)
    println("nTmp =" + nTmp)

    val tmp = tools1.sum1("a", 2, 3, 4, 5, 6)
    println("!!!!!!!!!!!!=" + tmp)
  }
}
