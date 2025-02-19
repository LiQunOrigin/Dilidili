package com.liqun.dilidili.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service.config
 * @className: WebSocketConfig
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/19 9:10
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }// 注册ServerEndpointExporter,为了扫描使用@ServerEndpoint注解声明的websocket endpoint
}
