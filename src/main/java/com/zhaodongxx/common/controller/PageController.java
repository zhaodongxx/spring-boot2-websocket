package com.zhaodongxx.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhaodong on 2018/9/19 22:20
 */
@Controller
@Slf4j
public class PageController {

    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/app/chat");
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "chat";
    }

    /**
     * 即时通信
     */
    @GetMapping(value = "/app/chat")
    public String chat() {
//        MyUserDetails user = UserUtil.getCurrentUser();
//        model.addAttribute("username", user.getUsername());
        return "chat";
    }
}
