package com.acting.actrpc.consumer;

import com.acting.actrpc.config.RpcConfig;
import com.acting.actrpc.utils.ConfigUtils;

public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc= ConfigUtils.loadConfig(RpcConfig.class,"rpc");
        System.out.println(rpc);
    }
}
