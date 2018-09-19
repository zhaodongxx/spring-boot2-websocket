package com.zhaodongxx.common.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 握手拦截器
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/4/18 13:03
 */
@Component
@Slf4j
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        //log.info("____beforeHandshake");
        //log.info("{}", (serverHttpRequest instanceof ServletServerHttpRequest));

        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) serverHttpRequest;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
//            log.info("curr session {}", session.getId());

//            if (session != null) {
            //使用userName区分WebSocketHandler，以便定向发送消息
//            String httpSessionId = session.getId();
//            log.info(httpSessionId);
            try {
                map.put("JSESSIONID", "cad8bfe7-f125-4764-95b5-5f228e152c09");
                map.put("account", "test");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest,
                               ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        //log.info("____afterHandshake");
    }
}
