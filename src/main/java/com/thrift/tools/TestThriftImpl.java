package com.thrift.tools;

import com.thrift.tools.TestThrift.Iface;
import org.apache.thrift.TException;

/**
 * Created by wangxy on 16-10-14.
 */
public class TestThriftImpl implements Iface{

    public String qury(String name, int age) throws TException{
        System.out.println("name="+name+"  age="+age);
        return "hello world";
    }
}
