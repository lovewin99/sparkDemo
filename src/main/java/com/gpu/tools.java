package com.gpu;


import java.util.List;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * Created by wangxy on 16-8-1.
 */
public class tools {

    public static void Mat_to_vector_char(Mat m, List<Byte> bs) {
        if(bs == null) {
            throw new IllegalArgumentException("Output List can\'t be null");
        } else {
            int count = m.rows();
            if(m.cols() == 1) {
                bs.clear();
                byte[] buff = new byte[count];
                m.get(0, 0, buff);

                for(int i = 0; i < count; ++i) {
                    bs.add(Byte.valueOf(buff[i]));
                }

            } else {
                throw new IllegalArgumentException("m.cols()!=1\n" + m);
            }
        }
    }
}
