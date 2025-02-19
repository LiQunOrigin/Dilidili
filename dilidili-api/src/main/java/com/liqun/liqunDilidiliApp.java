package com.liqun;

import com.liqun.dilidili.service.websocket.WebSocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun
 * @className: liqunDilidiliApp
 * @author: LiQun
 * @description: 启动类
 * @data 2024/12/3 14:18
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
public class liqunDilidiliApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(liqunDilidiliApp.class, args);
        WebSocketService.setApplicationContext(app);
    }
}
