package com.zhaodongxx.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/9/11 19:36
 */
@RequestMapping("/app")
@Controller
public class ChatController {

    /**
     * 即时通信
     */
    @GetMapping(value = "/chat")
    public String chat() {
        return "chat";
    }
}
