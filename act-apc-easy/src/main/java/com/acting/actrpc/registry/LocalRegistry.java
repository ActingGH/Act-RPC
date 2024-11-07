package com.acting.actrpc.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地服务注册器
 *
 * 不同于注册中心
 * 本地服务器是根据服务名获取到对应的服务实现类
 * 注册中心 侧重于管理注册的服务 提供服务信息给消费者
 *
 * 服务提供者启动时 需要注册服务到注册器中
 */
public class LocalRegistry {
    /**
     * 注册信息存储
     */
    private static final Map<String,Class<?>> map=new ConcurrentHashMap<>();

    /**
     * 注册服务
     */
    public static void register(String serviceName,Class<?> implClass){
        map.put(serviceName,implClass);
    }
    /**
     * 获取服务
     */
    public static Class<?> get(String name){
        return map.get(name);
    }

    /**
     * 删除服务
     */
    public static void remove(String serviceName){
        map.remove(serviceName);
    }




}
