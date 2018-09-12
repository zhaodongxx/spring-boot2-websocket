package com.zhaodongxx.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/9/11 20:28
 */
@Controller
@Slf4j
public class LoginController {

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
}
