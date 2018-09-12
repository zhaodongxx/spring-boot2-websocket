package com.zhaodongxx.common.message.service;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/4/17 13:29
 */
public interface MsgService {
    /**
     * 给所有用户发送系统消息，默认保存
     *
     * @param message 消息
     */
    void send(String message);

    /**
     * 给所有用户发送系统消息
     *
     * @param message 消息
     * @param doSave 是否保存消息
     */
    void send(String message, boolean doSave);

    /**
     * 根据账号向指定用户发送消息，默认保存
     *
     * @param message 消息
     * @param account 消息接受者的账号
     */
    void send(String message, String account);

    /**
     * 根据账号向指定用户发送消息
     *
     * @param message 消息
     * @param account 消息接受者的账号
     * @param doSave 是否保存消息
     */
    void send(String message, String account, boolean doSave);
}
