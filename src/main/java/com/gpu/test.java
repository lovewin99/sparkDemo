//package com.gpu;
//
//import org.apache.spark.api.java.*;
//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.function.Function;
//import org.apache.hadoop.io.*;
//import scala.Tuple2;
//import java.util.List;
//import org.apache.spark.api.java.function.PairFunction;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
////import org.opencv.imgcodecs.Imgcodecs; (opencv 3.0)
//import org.opencv.highgui.*; //opencv 2.4.9
//import org.opencv.utils.Converters;
//import java.util.Arrays;
//import org.apache.commons.io.FilenameUtils;
//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;
//
///**
// *
// * Created by wangxy on 16-7-29.
// */
//public class test {
//
//
////    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
////
////    static{ System.loadLibrary("opencv-2410.jar"); }
//
//    public static void main(String[] args) {
//        String imagesSequenceFile = "/home/wangxy/data/3d/"; //change this
//        String metadataSequenceFile = "/home/wangxy/data/3d1/";  //change this
//        SparkConf conf = new SparkConf().setAppName("SequenceFile Test").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//
//        //1) get a non serializable RDD with the images
////        JavaPairRDD<Text,BytesWritable> imagesRDD_ = sc.sequenceFile(imagesSequenceFile, Text.class, BytesWritable.class);
//        JavaPairRDD<String,String> imagesRDD_ = sc.wholeTextFiles(imagesSequenceFile);
//
//        //2) get a serializable RDD with the information extracted from the images
//        JavaPairRDD<String, String> imagesRDD = imagesRDD_.mapToPair(
//                new PairFunction<Tuple2<Text, BytesWritable>, String, String>() {
//                    @Override
//                    public Tuple2<String, String> call(Tuple2<Text, BytesWritable> kv) throws Exception {
//                        String filename = kv._1().toString();
//                        String filenameWithoutExtension = FilenameUtils.getName(filename);
//                        Mat image = byteswritableToOpenCVMat(kv._2());
//                        return new Tuple2(filenameWithoutExtension, image.cols() + "");
//                    }
//                });
//
//        //3) get a non serializable RDD with the metadata
//        JavaPairRDD<Text,BytesWritable> metadataRDD_ = sc.sequenceFile(metadataSequenceFile, Text.class, BytesWritable.class);
//        JavaPairRDD<String, String> metadataRDD = metadataRDD_.mapToPair(new PairFunction<Tuple2<Text,BytesWritable>,String,String>() {
//            @Override
//            public Tuple2<String, String> call(Tuple2<Text, BytesWritable> kv) throws Exception {
//                String metadataString = byteswritableToString(kv._2());
//
//                String userName = "";
//                try {
//                    System.out.println("parsing:"+metadataString);
//                    JSONParser parser = new JSONParser();
//                    JSONObject jsonObject  = (JSONObject) parser.parse(metadataString.trim());
//                    JSONObject userObj = (JSONObject) jsonObject.get("user");
//                    userName = (String) userObj.get("username");
//                } catch (Exception e) {
//                    System.out.println("error parsing file "+kv._1().toString()+": "+e.getMessage());
//                    e.printStackTrace();
//                }
//                String filename = kv._1().toString();
//                String filenameWithoutExtension = FilenameUtils.getBaseName(filename);
//                return new Tuple2(filenameWithoutExtension, userName);
//            }
//        });
//        //4) join both RDDs
//        JavaPairRDD<String, Tuple2<String, String>> joinRDD = imagesRDD.join(metadataRDD);
//        List<Tuple2<String, Tuple2<String, String>>> output3 = joinRDD.collect();
//        for (Tuple2<String,Tuple2<String, String>> tuple : output3) {
//            System.out.println(tuple._1() + ": "+tuple._2()._1() + ": "+tuple._2()._2());
//        }
//    }
//
//    /* HELPER FUNCTIONS */
//    public static Mat byteswritableToOpenCVMat(BytesWritable inputBW) {
//        byte[] imageFileBytes = inputBW.getBytes();
//        Mat img = new Mat();
//        Byte[] bigByteArray = new Byte[imageFileBytes.length];
//        for (int i=0; i<imageFileBytes.length; i++)
//            bigByteArray[i] = new Byte(imageFileBytes[i]);
//        List<Byte> matlist = Arrays.asList(bigByteArray);
//        img = Converters.vector_char_to_Mat(matlist);
//        //img = Imgcodecs.imdecode(img, Imgcodecs.CV_LOAD_IMAGE_COLOR); OPENCV 3.0
//        img = Highgui.imdecode(img, Highgui.CV_LOAD_IMAGE_COLOR);
//        return img;
//    }
//
//    public static String byteswritableToString(BytesWritable inputBW) {
//        byte[] imageFileBytes = inputBW.getBytes();
//        String metadataString = new String(imageFileBytes, 0, imageFileBytes.length, java.nio.charset.Charset.forName("ISO-8859-1"));
//        return metadataString;
//    }
//
//}
