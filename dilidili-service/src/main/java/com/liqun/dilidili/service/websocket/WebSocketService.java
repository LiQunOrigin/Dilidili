package com.liqun.dilidili.service.websocket;

import com.alibaba.fastjson.JSONObject;
import com.liqun.dilidili.domain.Danmu;
import com.liqun.dilidili.service.DanmuService;
import com.liqun.dilidili.service.utils.TokenUtil;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @projectName: Dilidili
 * @package: com.liqun.dilidili.service.websocket
 * @className: WebSocketService
 * @author: LiQun
 * @description: TODO
 * @data 2025/2/19 9:12
 */

@Component
@ServerEndpoint("/imserver/{token}")
public class WebSocketService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);// 在线人数

    private static final ConcurrentHashMap<String, WebSocketService> WEB_SOCKET_MAP = new ConcurrentHashMap<>();// 客户端连接池

    private Session session;// 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String sessionId;
    private Long userId;

    private static ApplicationContext APPLICATION_CONTEXT;// spring上下文

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketService.APPLICATION_CONTEXT = applicationContext;
    }

    @OnOpen
    public void openConnection(Session session, @PathParam("token") String token) {
        try {
            this.userId = TokenUtil.verifyToken(token);
        } catch (Exception ignored) {
        }
        this.sessionId = session.getId();
        this.session = session;
        if (WEB_SOCKET_MAP.containsKey(sessionId)) {
            WEB_SOCKET_MAP.remove(sessionId);
            WEB_SOCKET_MAP.put(sessionId, this);
        } else {
            WEB_SOCKET_MAP.put(sessionId, this);
            ONLINE_COUNT.getAndIncrement();
        }
        logger.info("用户连接成功: " + sessionId + ", 当前在线人数: " + ONLINE_COUNT.get());
        try {
            this.sendMessage("0");
        } catch (Exception e) {
            logger.error("连接异常");
        }
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void closeConnection() {
        if (WEB_SOCKET_MAP.containsKey(sessionId)) {
            WEB_SOCKET_MAP.remove(sessionId);
            ONLINE_COUNT.getAndDecrement();
        }
        logger.info("用户退出: " + sessionId + ", 当前在线人数: " + ONLINE_COUNT.get());
    }

    @OnMessage
    public void onMessage(String message) {
        logger.info("用户信息: " + sessionId + ", 消息: " + message);
        if (!StringUtil.isNullOrEmpty(message)) {
            try {
                //群发消息
                for (Map.Entry<String, WebSocketService> entry : WEB_SOCKET_MAP.entrySet()) {
                    WebSocketService webSocketService = entry.getValue();
                    if (webSocketService.session.isOpen()) {
                        webSocketService.sendMessage(message);
                    }
                }
                if(this.userId != null) {
                    //保存弹幕到数据库
                    Danmu danmu = JSONObject.parseObject(message, Danmu.class);
                    danmu.setUserId(userId);
                    danmu.setCreateTime(new Date());
                    DanmuService danmuService = (DanmuService)APPLICATION_CONTEXT.getBean(DanmuService.class);
                    danmuService.addDanmu(danmu);
                    //保存弹幕到redis
                    danmuService.addDanmusToRedis(danmu);
                }
            } catch (Exception e) {
                logger.error("弹幕接收出现问题");
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Throwable error) {
    }

}
