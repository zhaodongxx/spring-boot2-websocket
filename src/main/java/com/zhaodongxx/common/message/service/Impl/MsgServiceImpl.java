package com.zhaodongxx.common.message.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhaodongxx.common.message.model.Message;
import com.zhaodongxx.common.message.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/4/18 10:27
 */
@Slf4j
@Service
public class MsgServiceImpl implements MsgService {
    private static final String ALL = "IM_ALL";
    private static final String TO_ALL_DESTINATION = "/topic/chat";
    private static final String SYSTEM_NOTICE_DESTINATION = "/topic/notice";
    private static final String TO_USER_DESTINATION = "/message";

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 给所有用户发送 系统消息
     *
     * @param message 消息
     */
    @Override
    public void send(String message) {
        this.send(message, ALL);
    }

    /**
     * 个人用户发送全局消息
     *
     * @param message 消息
     * @param sender  消息发送者
     */
    @Override
    public void send(String message, String sender) {
        this.send(message, sender, ALL);
    }

    /**
     * 给指定用户发送消息
     *
     * @param message 消息
     * @param account 消息接受者的账号
     */
    @Override
    public void send(String message, String sender, String account) {

        Message msg = new Message()
                .setSendTime(new Date())
                .setContent(message)
                .setSender(sender);

        if (StringUtils.isNotEmpty(account)) {
            msg.setReceiver(account);
        } else {
            msg.setReceiver(ALL);
        }
        this.send(msg);
    }

    private void send(Message message) {
        String receiver = message.getReceiver();
        switch (receiver) {
            case ALL:
                sendToAll(message);
                break;
            default:
                sendToUser(message);
                break;
        }
    }

    private void sendToAll(Message message) {
        log.info("【全服消息】 {} -> {} :   ", message.getSender(), message.getReceiver(), message.getContent());
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        template.convertAndSend(TO_ALL_DESTINATION,
                JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
    }

    private void sendToUser(Message message) {
        log.info("【个人消息】 {} -> {} :   ", message.getSender(), message.getReceiver(), message.getContent());
        String receiver = message.getReceiver();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        template.convertAndSendToUser(receiver,
                TO_USER_DESTINATION,
                JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
    }
}


