package com.test

/**
 * Created by wangxy on 15-5-25.
 */
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
}
