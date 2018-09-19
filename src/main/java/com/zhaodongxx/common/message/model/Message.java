package com.zhaodongxx.common.message.model;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>  </p>
 *
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/4/17 13:25
 */
@Slf4j
@Accessors(chain = true)
@Data
@Component
public class Message {

    /**
     * 消息发送者：系统或者用户名
     */
    private String sender;

    /**
     * 消息接受者：用户账号
     */
    private String receiver;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Date sendTime;
}
