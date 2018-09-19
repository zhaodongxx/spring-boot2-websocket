package com.zhaodongxx.common.controller;

import com.zhaodongxx.common.message.service.MsgService;
import com.zhaodongxx.common.utils.restResult.RestResult;
import com.zhaodongxx.common.utils.restResult.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/9/14 13:44
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class TestController {

    @Resource
    private MsgService msgService;

    /**
     * 给指定用户发消息,默认不保存
     *
     * @param content
     * @param account
     * @return
     */
    @PostMapping("/msg/all")
    public RestResult msgToUser(@RequestParam String content,
                                @RequestParam(required = false) String account,
                                @RequestParam(defaultValue = "false") boolean doSave) {
        msgService.send(content, account);
        return ResultGenerator.genSuccessResult();
    }
}
