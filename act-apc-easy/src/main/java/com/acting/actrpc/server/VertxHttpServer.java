package com.acting.actrpc.server;

import io.vertx.core.Vertx;


public class VertxHttpServer implements HttpServer {


    @Override
    public void doStart(int port) {
        //创建Vert.x的实例
        Vertx vertx = Vertx.vertx();

        //创建http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
                    //处理http请求
                    System.out.println("received request:" + request.method() + " " + request.uri());
                    request.response()
                            .putHeader("content-type", "text/plain")
                            .end("Hello from Vert.x HTTP server!");
                }
        );
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port " + port);
            } else {
                System.out.println("Failed to Start server: " + result.cause());

            }
        });

    }




}
