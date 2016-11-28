package com.hadoop.com;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Created by wangxy on 16-8-1.
 */
public class ListByteWritable implements Writable {

    private ArrayList<Byte> data = null;

    public ListByteWritable(){

    }

    public ListByteWritable(ArrayList<Byte> d){
        data = d;
    }

    public void setData(ArrayList<Byte> d){
        data = d;
    }

    public void readFields(DataInput in) throws IOException{

    }

    public void write(DataOutput out) throws IOException{
//        byte[] array = (byte[])data.ToArray(typeof(byte));
//        byte[] arr = (byte[])data.toArray(new byte(data.size()));

        byte[] tmp = new byte[data.size()];
        for(int i = 0; i < data.size(); i++){
            tmp[i] = data.get(i);
        }
        out.write(tmp);
    }

}
