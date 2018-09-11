package com.zhaodongxx.common.security;

import com.alibaba.fastjson.JSON;
import com.zhaodongxx.common.utils.restResult.RestResult;
import com.zhaodongxx.common.utils.restResult.ResultGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/7/18 16:05
 */
@Slf4j
@Configuration
public class MyLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (!ObjectUtils.isEmpty(authentication)) {
            responseResult(response, ResultGenerator.genSuccessResult().setMessage("退出登录成功"));
        } else {
            responseResult(response, ResultGenerator.genFailResult("请登录"));
        }
    }

    private void responseResult(HttpServletResponse response, RestResult result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
