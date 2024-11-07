package com.acting.actrpc.proxy;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.acting.actrpc.model.RpcRequest;
import com.acting.actrpc.model.RpcResponse;
import com.acting.actrpc.serializer.Serializer;
import com.acting.actrpc.serializer.jdkSerializer;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Serializer serializer=new jdkSerializer();
        RpcRequest rpcRequest=RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args).build();
        try{
            //序列化
            byte[] bodyBytes= serializer.serialize(rpcRequest);
            try(HttpResponse httpResponse= HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes).execute()){
                byte[] result= httpResponse.bodyBytes();
                RpcResponse rpcResponse=serializer.deserialize(result, RpcResponse.class);
                return rpcResponse.getData();
            }
        }catch(IOException e){
            e.printStackTrace();
        }


        return null;

    }
}
