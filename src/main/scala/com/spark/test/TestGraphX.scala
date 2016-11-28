package com.spark.test

/**
 * Created by wangxy on 16-4-19.
 */

import org.apache.spark._
import org.apache.spark.graphx._
// To make some of the examples work we will also need RDD
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}



object TestGraphX {

//  class Graph[VD, ED] {
//    val vertices: VertexRDD[VD]
//    val edges: EdgeRDD[ED]
//  }

  class VertexProperty()
  case class UserProperty(name: String) extends VertexProperty
  case class ProductProperty(name: String, price: Double) extends VertexProperty
  // The graph might then have the type:
  var graph: Graph[VertexProperty, String] = null


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val sc = new SparkContext(conf)

    // Create an RDD for the vertices
    val users: RDD[(VertexId, (String, String))] =
      sc.parallelize(Array((3L, ("rxin", "student")), (7L, ("jgonzal", "postdoc")),
        (5L, ("franklin", "prof")), (2L, ("istoica", "prof"))))
    // Create an RDD for edges
    val relationships: RDD[Edge[String]] =
      sc.parallelize(Array(Edge(3L, 7L, "collab"),    Edge(5L, 3L, "advisor"),
        Edge(2L, 5L, "colleague"), Edge(5L, 7L, "pi")))
    // Define a default user in case there are relationship with missing user
    val defaultUser = ("John Doe", "Missing")
    // Build the initial Graph
    val graph = Graph(users, relationships, defaultUser)

    // Count all users which are postdocs
    graph.vertices.filter { case (id, (name, pos)) => pos == "postdoc" }.count
    // Count all the edges where src > dst
    graph.edges.filter(e => e.srcId > e.dstId).count


  }

}
