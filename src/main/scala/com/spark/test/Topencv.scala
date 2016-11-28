package com.spark.test

import org.apache.hadoop.mapred.FileOutputFormat
import org.apache.spark.input.PortableDataStream
import org.apache.spark.{SparkContext, SparkConf}
import org.opencv.core._
import org.opencv.highgui._
import org.opencv.utils.Converters
import org.opencv.imgproc.Imgproc

//import java.{lang => jl, util => ju, util}
//import java.util.List

//import scala.collection.JavaConversions._
import scala.collection.JavaConversions.seqAsJavaList
import com.gpu.tools

import com.hadoop.com._
import org.apache.hadoop.io.NullWritable

/**
 *
 * Created by wangxy on 16-7-29.
 */
object Topencv {

//  public static Mat byteswritableToOpenCVMat(BytesWritable inputBW) {
//    byte[] imageFileBytes = inputBW.getBytes();
//    Mat img = new Mat();
//    Byte[] bigByteArray = new Byte[imageFileBytes.length];
//    for (int i=0; i<imageFileBytes.length; i++)
//    bigByteArray[i] = new Byte(imageFileBytes[i]);
//    List<Byte> matlist = Arrays.asList(bigByteArray);
//    img = Converters.vector_char_to_Mat(matlist);
//    //img = Imgcodecs.imdecode(img, Imgcodecs.CV_LOAD_IMAGE_COLOR); OPENCV 3.0
//    img = Highgui.imdecode(img, Highgui.CV_LOAD_IMAGE_COLOR);
//    return img;
//  }

  def main(args: Array[String]): Unit = {

//    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
//    val image: Mat = Highgui.imread("/home/wangxy/data/3d/kermit000.jpg")
//    System.out.println("rows=" + image.row(0))

    val conf = new SparkConf
    val sc = new SparkContext("local", "Topencv", conf)

    val rdd = sc.binaryFiles("/home/wangxy/data/3d/*")
    rdd.mapPartitions{iter =>
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
      iter.map{
        case (k, v) =>

          println(s"k=$k")

          val matlist: java.util.List[java.lang.Byte] = v.toArray().toList.map(_.asInstanceOf[java.lang.Byte])

          val img = Converters.vector_char_to_Mat(matlist)

          val img1 = Highgui.imdecode(img, Highgui.CV_LOAD_IMAGE_COLOR)

          val dsize = new Size(img1.size().width / 2.0, img1.size().height / 2.0)

          val dstMat = new Mat()

          Imgproc.resize(img1, dstMat, dsize)

          println("1")

//          val matOfByte1 = MatOfByte.fromNativeAddr(1)

          println("2")
          val matOfByte1 = new MatOfByte

          Highgui.imencode(".jpg", dstMat, matOfByte1)

          var jList: java.util.List[java.lang.Byte] = List()

          val ll: java.util.ArrayList[java.lang.Byte] = new java.util.ArrayList[java.lang.Byte]()

          println(s"CvType.CV_8SC1 = ${CvType.CV_8SC1}")
          println(s"img = ${img.`type`()}")
          println(s"dstMat = ${dstMat.`type`()}")
          println(s"matOfByte1= ${matOfByte1.`type`()}")
          println(s"matOfByte1.cols = ${matOfByte1.cols()}")
          println(s"matOfByte1.rows = ${matOfByte1.rows()}")
          println(matOfByte1.size())

//          dstMat.getNativeObjAddr

//          Highgui.imwrite("/home/wangxy/data/123.jpg", dstMat)


          tools.Mat_to_vector_char(matOfByte1, ll)

          val listw = new ListByteWritable(ll)

//          val key = new NullWritable

          println(s"ll = ${ll.size}")
          (NullWritable.get(), listw)
      }

    }.saveAsNewAPIHadoopFile[BinaryOutputFormat[NullWritable, ListByteWritable]]("/home/wangxy/data/3dtest/")

  }

}
