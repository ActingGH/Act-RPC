package com.acting.actrpc.config;

import lombok.Data;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {
    private String name = "acting";

    private String version = "1.0";

    private String serverHost = "localhost";

    private Integer serverPort = 8080;
}
