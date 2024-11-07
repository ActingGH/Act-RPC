package com.acting.actrpc.provider;

import com.acting.actrpc.common.service.UserService;
import com.acting.actrpc.registry.LocalRegistry;
import com.acting.actrpc.server.HttpServer;
import com.acting.actrpc.server.VertxHttpServer;
import io.vertx.core.Vertx;


public class EasyProviderExample {
    public static void main(String[] args){
       //注册服务
        LocalRegistry.register(UserService.class.getName(),UserServiceImpl.class);

        //启动服务
        HttpServer httpServer=  new VertxHttpServer();
        httpServer.doStart(8080);

    }
}
