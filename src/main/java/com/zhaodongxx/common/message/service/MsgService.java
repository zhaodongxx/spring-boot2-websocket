package com.zhaodongxx.common.message.service;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/4/17 13:29
 */
public interface MsgService {

    /**
     * 给所有用户发送 系统消息
     *
     * @param message 消息
     */
    void send(String message);

    /**
     * 个人用户发送全局消息
     *
     * @param message 消息
     * @param sender 消息发送者
     */
    void send(String message, String sender);

    /**
     * 根据账号向指定用户发送消息
     *
     * @param message 消息
     * @param sender 消息发送者
     * @param account 消息接受者的账号
     */
    void send(String message, String sender,String account);
}
