package com.zhaodongxx.common.controller;

import com.zhaodongxx.common.message.model.MsgInpDTO;
import com.zhaodongxx.common.message.service.MsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/9/11 19:36
 */
@Controller
@Slf4j
public class ChatController {

    @Autowired
    private MsgService msgService;

    @MessageMapping(value = "/chat")
    //@SendTo("/topic/notice")
    public void handleChat(MsgInpDTO msgInpDTO) {
        msgService.send(msgInpDTO.getContent(), msgInpDTO.getSender(),msgInpDTO.getReceiver());
    }
}
