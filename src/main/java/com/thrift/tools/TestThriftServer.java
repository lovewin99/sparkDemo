package com.thrift.tools;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportFactory;

/**
 *
 * Created by wangxy on 16-10-14.
 */
public class TestThriftServer {
    public void startServer(int port){
        try {
            // ==================== 啓動 thrift ========================

            // 传输通道 - 非阻塞方式
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(port);

            // 异步IO，需要使用TFramedTransport，它将分块缓存读取。
            TTransportFactory transportFactory = new TFramedTransport.Factory();

            // 使用高密度二进制协议
            TProtocolFactory proFactory = new TCompactProtocol.Factory();

            // 设置处理器 Impl
            @SuppressWarnings({ "rawtypes", "unchecked" })
            TProcessor processor = new TestThrift.Processor(new TestThriftImpl());
            // 创建服务器
            TServer server = new TThreadedSelectorServer(new TThreadedSelectorServer.Args(serverTransport)
                    .protocolFactory(proFactory).workerThreads(20).transportFactory(transportFactory).processor(processor));
            System.out.println(" thrift server  start, port : " + port);
            server.serve();
            System.out.println("=============");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        if(1 != args.length){
//            System.err.println("Need  parameters: port");
//            System.exit(0);
//        }

        TestThriftServer ynserver = new TestThriftServer();
        ynserver.startServer(10001);
    }
}
