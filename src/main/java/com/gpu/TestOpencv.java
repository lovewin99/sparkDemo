package com.gpu;

import org.opencv.core.*;
import org.opencv.highgui.*;

/**
 *
 * Created by wangxy on 16-7-29.
 */
public class TestOpencv {

    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("/home/wangxy/data/3d/kermit000.jpg");
        System.out.println("rows="+image.row(0));

    }
}
