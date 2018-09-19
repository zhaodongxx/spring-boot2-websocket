package com.zhaodongxx.common.controller;

import com.zhaodongxx.common.security.MyUserDetails;
import com.zhaodongxx.common.security.UserUtil;
import com.zhaodongxx.common.utils.restResult.RestResult;
import com.zhaodongxx.common.utils.restResult.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhaodong on 2018/9/19 23:33
 */
@RestController
@Slf4j
@RequestMapping("/api")
public class APIController {
    @GetMapping("/userInfo")
    public RestResult msgToUser() {

        MyUserDetails user = UserUtil.getCurrentUser();
        Map<String, String> ret = new HashMap<>();
        ret.put("username", user.getUsername());
        ret.put("avatar", "/avatar/user.jpg");
        return ResultGenerator.genSuccessResult(ret);
    }
}
