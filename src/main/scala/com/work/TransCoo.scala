package com.work

import com.tools.CoorTransformUtils

import scala.io.Source
import java.io.{PrintWriter, FileInputStream, File}

/**
 *
 * Created by wangxy on 16-9-1.
 */
object TransCoo {

  def main(args: Array[String]): Unit = {
    val files = Source.fromFile("/home/wangxy/data/buchong.csv").getLines()
    val writer = new PrintWriter(new File("/home/wangxy/data/outbc.csv"),"UTF-8")
    val info = files.toArray.foreach{s=>
      val strArr = s.split(",", -1)
      val (x,y) = CoorTransformUtils.lonlat2grid(strArr(2).toDouble, strArr(3).toDouble)
      writer.println(s+","+x+","+y)
    }

    for (i: Int <- 0 to 10){

    }

  }

}
