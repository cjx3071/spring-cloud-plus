package org.gourd.hu.notice.websocket;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket端点
 *
 * @author gourd.hu
 */
@ServerEndpoint(path = "/ws/{arg}", port = "${netty-websocket.port}")
@Component
@Slf4j
public class NioWebSocket {


    private static final String successFlag = "OK";

    /**
     * 用户channel映射关系
     */
    public static final ConcurrentHashMap<String, Session> userChannelMap = new ConcurrentHashMap<>();

    /**
     * 当有新的连接进入时，对该方法进行回调
     * 可做简单的鉴权
     * @param session
     * @param headers
     * @param req
     * @param reqMap
     * @param arg
     * @param pathMap
     */
    @BeforeHandshake
    public void handshake(Session session, HttpHeaders headers,
                          @RequestParam String req,
                          @RequestParam MultiValueMap reqMap,
                          @PathVariable String arg,
                          @PathVariable Map pathMap) {
        session.setSubprotocols("stomp");
//        if (!successFlag.equalsIgnoreCase(req)) {
//            session.close();
//        }
    }

    /**
     * 当有新的WebSocket连接完成时，对该方法进行回调
     *
     * @param session
     * @param headers
     * @param req
     * @param reqMap
     * @param arg
     * @param pathMap
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers,
                       @RequestParam String req,
                       @RequestParam MultiValueMap reqMap,
                       @PathVariable String arg,
                       @PathVariable Map pathMap){
        log.info("new connection");
        // 模拟获取到的userId
        String userId = arg;
        if (userChannelMap.get(userId) == null) {
            userChannelMap.put(userId, session);
        }
        log.info("当前在线人数：" + userChannelMap.size());
    }

    /**
     * 当有WebSocket连接关闭时，对该方法进行回调
     *
     * @param session
     * @param arg
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session,@PathVariable String arg) {
        log.info("one connection closed");
        if (userChannelMap != null) {
            if(userChannelMap.get(arg) != null){
                userChannelMap.remove(arg);
            }
        }
        session.close();
        log.info("当前在线人数：" + userChannelMap.size());
    }

    /**
     * 当有WebSocket抛出异常时，对该方法进行回调
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session,@PathVariable String arg,Throwable throwable) {
        log.info("one connection error");
        log.error(throwable.getMessage(), throwable);
        this.onClose(session,arg);
    }

    /**
     * 当接收到字符串消息时，对该方法进行回调
     *
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("接收到消息: {}",message);
        sendMessageToUser(session, message);
    }

    /**
     * 当接收到二进制消息时，对该方法进行回调
     * @param session
     * @param bytes
     */
    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        session.sendBinary(bytes);
    }

    /**
     * 当接收到Netty的事件时，对该方法进行回调
     *
     * @param session
     * @param evt
     */
    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    log.info("Client: " + session.channel().id() + " READER_IDLE 读超时");
                    break;
                case WRITER_IDLE:
                    log.info("Client: " + session.channel().id() + " WRITER_IDLE 写超时");
                    break;
                case ALL_IDLE:
                    log.info("Client: " + session.channel().id() + " ALL_IDLE 总超时");
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 单点推送给某个人
     *
     * @param session
     * @param msg
     */
    public static final void sendMessageToUser(Session session, String msg) {
        if(session.isOpen() && session.isActive()){
            TextWebSocketFrame tws = new TextWebSocketFrame(msg);
            session.sendText(tws);
        }
    }

    /**
     * 广播给所有在线的人
     *
     * @param msg
     */
    public static final void broadcast(String msg) {
        if (userChannelMap != null) {
            userChannelMap.forEach((key, value) -> sendMessageToUser(value, msg));
        }
    }
}