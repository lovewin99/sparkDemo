package com.scalacode

/**
 * Created by wangxiaoyong on 2014/12/5.
 */
import java.io.{FileInputStream, File}
import scala.io.Source
object file9 {
  def main(args: Array[String]): Unit ={
  // 文件和正则表达式
  // 读取行
      val source = Source.fromFile("config/myfile", "UTF-8");
      val lineInterator = source .getLines()
      for(l <- lineInterator){
        println("words = " + l.toString)
      }

  //与shell进行交互
  //    "ls -al .." !

      val file = new File("config/myfile")
      val in = new FileInputStream(file)
      val bytes = new Array[Byte](file.length.toInt)
      in.read(bytes)
      in.close()
  }
}
