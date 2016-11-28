package com.thrift.tools;

import org.apache.log4j.Logger;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 *
 * Created by wangxy on 16-10-14.
 */
public class TestThriftClinet {

    private static Logger logger = Logger.getLogger(TestThriftClinet.class);

    public void startClient(String ip){
        TTransport transport;
        try {
            transport = new TFramedTransport(new TSocket(ip, 10001));

            TProtocol protocol = new TCompactProtocol(transport);

            TestThrift.Client client = new TestThrift.Client(protocol);
            transport.open();
            //String result = client.qryNpno("352824061116614", 1455872837741L, 115, 1);
            //System.out.println(result);
            String result1 = client.qury("hello", 18);
            System.out.println(result1);
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        TestThriftClinet tcl= new TestThriftClinet();
//        tcl.startClient("10.95.4.126");
		tcl.startClient("127.0.0.1");
    }

}
