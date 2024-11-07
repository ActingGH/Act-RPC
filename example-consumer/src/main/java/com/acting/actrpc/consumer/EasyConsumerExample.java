package com.acting.actrpc.consumer;

import com.acting.actrpc.server.HttpServer;
import com.acting.actrpc.server.VertxHttpServer;

public class EasyConsumerExample {
    public static void main(String[] args) {
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
