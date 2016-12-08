package com.test

/**
  * Created by wangxy on 2016/12/8.
  */


package parents1{

  import com.test.parents1.child1.c1

  package child1{


    class c1{

      private [this] var t1 = 0

      def voice = println(s"t1 v = $t1")

    }

    object Testc{

      def main(args: Array[String]): Unit = {
        val c = new c1
//        println(s"c.t1 = ${c.t1}")
        c.voice
      }

    }


  }

  object Test1{

    def main(args: Array[String]): Unit = {
      val c = new c1
//      println(s"c.t1 11 = ${c.t1}")
      c.voice
    }

  }

}
