package com.acting.actrpc.server;

import com.acting.actrpc.model.RpcRequest;
import com.acting.actrpc.model.RpcResponse;
import com.acting.actrpc.registry.LocalRegistry;
import com.acting.actrpc.serializer.Serializer;
import com.acting.actrpc.serializer.jdkSerializer;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 请求处理器
 * 反序列化请求为对象 并从请求对象中获取参数
 * 根据服务名称从本地服务注册器中获取到对应的服务实现类
 * 通过反射机制的调用方法 得到返回结果
 * 对返回结果进行封装和序列化 并写入大响应中
 */
public class HttpServerHandler implements Handler<HttpServerRequest> {


    @Override
    public void handle(HttpServerRequest request) {
       //指定序列化器
        final Serializer serializer=new jdkSerializer();
        //记录日志
        System.out.println("Received request: "+request.method()+" "+request.uri());
        //一步处理http请求
        request.bodyHandler(body->{
            byte[] bytes= body.getBytes();
            RpcRequest rpcRequest=null;
            try{
                rpcRequest=serializer.deserialize(bytes, RpcRequest.class);
            }catch (Exception e){
                e.printStackTrace();
            }

            //构造响应结果对象
            RpcResponse response=new RpcResponse();
            if(rpcRequest ==null) {
                response.setMessage("rpcRequest is null");
                doResponse(request, response, serializer);
            }

            try {
                //获取到要调用的服务实现类 通过反射调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                //封装返回结果
                response.setData(result);
                response.setDataType(method.getReturnType());
                response.setMessage("ok");
            }catch(Exception e){
                e.printStackTrace();
            }
            doResponse(request,response,serializer);

        });

    }

    /**
     * 响应
     * @param request
     * @param response
     * @param serializer
     */
    private void doResponse(HttpServerRequest request, RpcResponse response, Serializer serializer) {
        HttpServerResponse httpServerResponse= request.response()
                .putHeader("content-type","application/json");
        try{
            //序列化
            byte[] serialized= serializer.serialize(response);
            httpServerResponse.end(Buffer.buffer(serialized));
        }catch (IOException e){
            e.printStackTrace();
            httpServerResponse.end(Buffer.buffer());
        }
    }


}
