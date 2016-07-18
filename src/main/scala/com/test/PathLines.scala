package com.test

import java.io.{File, PrintWriter}

import scala.io.Source

/**
 * Created by wangxy on 16-5-7.
 */
object PathLines {

  def main(args: Array[String]): Unit = {
    val inpath = ""
    val outpath = ""

    Source.fromFile(inpath)
    val writer = new PrintWriter(new File(outpath),"UTF-8")
  }

}
