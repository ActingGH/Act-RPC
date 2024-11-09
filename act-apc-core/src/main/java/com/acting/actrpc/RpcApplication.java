package com.acting.actrpc;

import com.acting.actrpc.config.RpcConfig;
import com.acting.actrpc.constant.RpcConstant;
import com.acting.actrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 *RPC框架应用
 * 相当于holder 存放了项目全局用到的变量
 * 双检锁单例模式：
 * 支持在获取配置时才调用init方法实现懒加载
 * 为了便于扩展 支持自己传入配置对象
 */

@Slf4j
public class RpcApplication {
    //关键字 volatile 确保在多线程的环境下 可见性和有序性
    //当一个线程修改了该线程的值之后 其他线程能立即看到这个变化
    private static volatile RpcConfig rpcConfig;

    /**
     * 初始化 支持传入自定义
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig){
        rpcConfig=newRpcConfig;
        log.info("rpc init,config={}",newRpcConfig.toString());
    }

    /**
     * 初始化
     */
    public static void init(){
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_CONFIG_PREDIX);
        }catch (Exception e){
            newRpcConfig=new RpcConfig();
        }
        init(newRpcConfig);
    }

    /**
     * 获取配置
     * @return
     */
    public static RpcConfig getRpcConfig(){
        if(rpcConfig==null){
            // 当一个线程进入同步代码块时 他会获取该对象的锁  TODO 撰写同步代码块的使用方法
            //其他线程必须等待当前线程释放该锁后 才能进入该同步代码块
            synchronized (RpcApplication.class){
                if(rpcConfig==null){
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
