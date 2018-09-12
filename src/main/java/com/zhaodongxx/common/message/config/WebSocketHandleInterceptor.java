package com.zhaodongxx.common.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;

/**
 * 输入消息拦截器
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/5/7 11:43
 */
@Component
@Slf4j
public class WebSocketHandleInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public boolean preReceive(MessageChannel channel) {
        System.out.println(this.getClass().getCanonicalName() + " preReceive");
        return super.preReceive(channel);
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        log.info("join WebSocketHandleInterceptor preSend");

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info(accessor.getCommand().name());

        if (StompCommand.CONNECT.equals(accessor.getCommand())||StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            Principal username = accessor.getUser();
            if (StringUtils.isEmpty(username)){
                return null;
            }
            // 绑定user
            accessor.setUser(username);
        }
        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.info(" afterSendCompletion");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)){
            log.info(this.getClass().getCanonicalName() + " 订阅消息发送成功");
//            this.simpMessagingTemplate.convertAndSend("/topic/notice","消息发送成功");
        }
        //如果用户断开连接
        if (StompCommand.DISCONNECT.equals(command)){
            log.info(this.getClass().getCanonicalName() + "用户断开连接成功");
//            simpMessagingTemplate.convertAndSend("/topic/getResponse","{'msg':'用户断开连接成功'}");
        }
        super.afterSendCompletion(message, channel, sent, ex);
    }
}
