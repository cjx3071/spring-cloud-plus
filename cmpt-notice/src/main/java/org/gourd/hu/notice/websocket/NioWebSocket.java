package org.gourd.hu.notice.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gourd.hu
 */
@ServerEndpoint(prefix = "netty-websocket")
@Component
@Slf4j
public class NioWebSocket {

    /**
     * 用户channel映射关系
     */
    public static final ConcurrentHashMap<String, Channel> userChannelMap = new ConcurrentHashMap<>();

    /**
     * 新的WebSocket连接进入
     * @param session
     * @param headers
     * @param parameterMap
     * @throws IOException
     */
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws IOException {
        log.info("new connection");
        // 模拟获取到的userId，companyId
        String userId = parameterMap.getParameterValues("userId").get(0);
        if(userChannelMap.get(userId) != null){
            // 踢除用户之前的连接
            Channel channel = userChannelMap.get(userId);
            channel.close();
        }
        userChannelMap.put(userId,session.channel());
        log.info("当前在线人数："+userChannelMap.size());
}

    /**
     * WebSocket连接关闭
     * @param session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        log.info("one connection closed");
        if(userChannelMap != null){
            // 断开连接的用户
            for(Map.Entry<String, Channel> entry : userChannelMap.entrySet()){
                if(session.channel().id().toString().equals(entry.getValue().id().toString())){
                    userChannelMap.remove(entry.getKey());
                    break;
                }
            }
        }
        log.info("当前在线人数："+userChannelMap.size());
    }

    /**
     * WebSocket抛出异常
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error(throwable.getMessage(),throwable);
    }

    /**
     * 接收到字符串消息
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        log.info(message);
        JSONObject jsonMessage = JSON.parseObject(message);
        String userId = jsonMessage.getString("userId");
        String body = jsonMessage.getString("body");
        if(StringUtils.isNotEmpty(userId)){
            sendMessageToUser(userId,body);
        }else {
            broadcast(body);
        }
        session.sendText("消息发送成功");
    }

    /**
     * 接收到二进制消息
     * @param session
     * @param bytes
     */
    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
        }
        session.sendBinary(bytes); 
    }

    /**
     * 接收到Netty的事件
     * @param session
     * @param evt
     */
    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    log.info("Client: "+session.channel().id()+" READER_IDLE 读超时");
                    break;
                case WRITER_IDLE:
                    log.info("Client: "+session.channel().id()+" WRITER_IDLE 写超时");
                    break;
                case ALL_IDLE:
                    log.info("Client: "+session.channel().id()+" ALL_IDLE 总超时");
                    break;
                default:
                    break;
            }
        }
    }


    /**
     * 单点推送给某个人
     *
     **/
    public static final void sendMessageToUser(String userId, String msg){
        Channel channel = userChannelMap.get(userId);
        TextWebSocketFrame tws = new TextWebSocketFrame( msg);
        channel.writeAndFlush(tws);
    }
    /**
     * 广播给所有在线的人
     *
     **/
    public static final void broadcast(String msg){
        if(userChannelMap != null){
            userChannelMap.forEach((key,value)->sendMessageToUser(key,msg));
        }
    }
}