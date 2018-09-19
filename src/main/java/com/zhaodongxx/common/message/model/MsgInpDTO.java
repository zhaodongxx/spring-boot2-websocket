package com.zhaodongxx.common.message.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by zhaodong on 2018/9/19 22:48
 */
@Data
@Slf4j
public class MsgInpDTO {

    /**
     * 发送者
     */
    private String sender;

    /**
     * 接收者
     */
    private String receiver;

    /**
     * 消息内容
     */
    private String content;
}
