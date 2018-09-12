package com.zhaodongxx.common.controller;

import com.zhaodongxx.common.security.MyUserDetails;
import com.zhaodongxx.common.security.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/9/11 19:36
 */
@Controller
@Slf4j
public class ChatController {

    /**
     * 即时通信
     */
    @GetMapping(value = "/app/chat")
    public String chat(Model model) {
        MyUserDetails user = UserUtil.getCurrentUser();
        log.debug(user.getUsername());
        log.debug(user.toString());
        log.debug("-------------------------");
        model.addAttribute("username", user.getUsername());
        return "chat";
    }

    /**
     * 即时通信
     */
    @MessageMapping(value = "/chat")
    @SendTo("/topic/notice")
    public String handleChat(String content, String receiver) {
        log.info("content  ------------" + content);
        return content;
    }


}
