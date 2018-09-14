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
    private final static long ONE_Minute = 60 * 1000;

    private static final String ALL = "IM_ALL";
    private static final String TO_ALL_DESTINATION = "/topic/notice";
    private static final String TO_USER_DESTINATION = "/message";

    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 给所有用户发送系统消息,默认保存消息
     *
     * @param message 消息
     */
    @Override
    public void send(String message) {
        this.send(message, true);
    }

    /**
     * 给所有用户发送系统消息,默认保存消息
     *
     * @param message 消息内容
     * @param doSave  是否保存消息
     */
    @Override
    public void send(String message, boolean doSave) {
        this.send(message, null, doSave);
    }

    /**
     * 给指定用户发送消息
     *
     * @param message 消息
     * @param account 消息接受者的账号
     */
    @Override
    public void send(String message, String account) {

        this.send(message, account, true);
    }

    /**
     * 给指定用户发送消息
     *
     * @param message 消息
     * @param account 消息接受者的账号
     * @param doSave  是否保存消息
     */
    @Override
    public void send(String message, String account, boolean doSave) {

        Message msg = new Message()
                .setSendTime(new Date())
                .setContent(message)
                .setSender("system")
                .setDoSave(doSave);

        if (StringUtils.isNotEmpty(account)) {
            msg.setReceiver(account);
        } else {
            msg.setReceiver(ALL);
        }
        this.send(msg);
    }

    private void send(Message message) {
        if (message.isDoSave()) {
            this.save(message);
        }

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

    //TODO 2018-4-20 15:40:05 赵东 在发送消息前确认用户是否在线
    //TODO 2018-4-20 15:40:05 赵东 返回消息ID

    private void sendToAll(Message message) {

        log.info("[发送][系统消息][{} -> {}] <{}>", message.getSender(), message.getReceiver(), message.getContent());

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        template.convertAndSend(TO_ALL_DESTINATION,
                JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
    }

    private void sendToUser(Message message) {

        log.info("[发送][系统消息][{} -> {}] <{}>", message.getSender(), message.getReceiver(), message.getContent());

        String receiver = message.getReceiver();
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        template.convertAndSendToUser(receiver,
                TO_USER_DESTINATION,
                JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
        message.getSendTime();
    }

    private void save(Message message) {
    }
}


