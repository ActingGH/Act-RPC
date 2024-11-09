package com.acting.actrpc.server;

import io.vertx.core.Vertx;


public class VertxHttpServer implements HttpServer {


    @Override
    public void doStart(int port) {
        //创建Vert.x的实例
        Vertx vertx = Vertx.vertx();

        //创建http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        server.requestHandler(new HttpServerHandler());

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.out.println("Failed to Start server: " + result.cause());

            }
        });

    }




}
