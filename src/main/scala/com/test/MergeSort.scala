package com.test

/**
 * Created by wangxy on 15-5-25.
 */
import org.apache.spark._

object MergeSort extends App {
  val oldList = List[Int](12, 40, 26, 89, 75, 44, 32, 65, 18)
  val list = mergeSort(oldList)
  println(list)
  def mergeSort(list:List[Int]):List[Int]={
    val n=list.size/2
    if(n==0)list
    else{
      val (xs,ys)=list.splitAt(n);
      merge(mergeSort(xs),mergeSort(ys))
    }
  }
  def merge(xs:List[Int],ys:List[Int]):List[Int]={
    (xs,ys) match{
      case (_,Nil) =>xs
      case (Nil,_) =>ys
      case (x::list1,y::list2)=> {
        if(x>y) y::merge(list2,xs)
        else x::merge(list1,ys)
      }
    }
  }

  def main(args: String): Unit = {

    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    val list = List((1,"a"),(2,"b"))
    val list1 = List(((1,"AA"),111),((1,"AAA"),222),((2,"BB"),333))
    val rdd = sc.parallelize(list)
    val rdd1 = sc.parallelize(list1)
    val rdd2 = rdd.join{
      rdd1.map{
        case ((t,w),u) => (t,(w,u))
      }
    }.map{
      case (t,(w,(u,v))) => ((t,u),(w,v))
    }.collect

    // join2 map
    val map = rdd.collectAsMap()
    val bc = sc.broadcast(map)
    rdd1.mapPartitions{iter =>
      val m = bc.value
      iter.map{
        case ((t,w),u) if m.contains(t) => ((t,w),(m.get(t).get,u))
      }
    }.collect

    //sort
    val data = Array[(String,Int,Int)](
      ("f",4,6),
      ("f",6,3),
      ("f",5,8),
      ("y",4,6),
      ("y",9,3),
      ("b",4,6),
      ("a",5,5),
      ("a",4,6)
    )
    val rdd3 = sc.parallelize(data)
    val test = rdd3.map{x => (x._1,(x._2,x._3))}
    test.groupByKey(3).map{k=>(k._1,k._2.toSeq.sortWith(_._1<_._1))}.sortByKey().collect.map(println _)
  }
}
