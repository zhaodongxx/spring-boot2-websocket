package com.zhaodongxx.common.message.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;

import java.util.List;

/**
 * <P>配置WebSocket</P>
 *
 * @author zhaodong
 * @version v1.0
 * @since 2018/2/7 14:41
 */

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@AllArgsConstructor
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置服务器要监听的端口
     * 这样在网页中就可以通过websocket连接上服务
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/any-socket")
                .setAllowedOrigins("*")
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .withSockJS();
    }

    /**
     * 配置消息代理
     *
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic","/user");
        registry.setUserDestinationPrefix("/user");
    }

    /**
     * 消息传输参数配置
     *
     * @param registration
     */
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        //设置消息字节数大小
        registration.setMessageSizeLimit(8 * 12024)
                //设置消息缓存大小
                .setSendBufferSizeLimit(8 * 1024)
                //设置消息发送时间限制毫秒
                .setSendTimeLimit(10000);

        registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(final WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {
//                    @Override
//                    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
//                        String username = session.getPrincipal().getName();
//                        log.info("online: " + username);
//                        super.afterConnectionEstablished(session);
//                    }

//                    @Override
//                    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
//                            throws Exception {
//                        String username = session.getPrincipal().getName();
//                        log.info("offline: " + username);
//                        super.afterConnectionClosed(session, closeStatus);
//                    }

                    /**
                     * 接受消息时被触发
                     * @param session
                     * @param message
                     * @throws Exception
                     */
                    @Override
                    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                        super.handleMessage(session, message);
                    }
                };
            }
        });
    }

    /**
     * 输入通道参数设置
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        //设置消息输入通道的线程池线程数
        registration.interceptors(new WebSocketHandleInterceptor())
                .taskExecutor()
                .corePoolSize(4)
                //最大线程数
                .maxPoolSize(8)
                //线程活动时间
                .keepAliveSeconds(30);
    }

    /**
     * 输出通道参数设置
     *
     * @param registration
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.taskExecutor()
                .corePoolSize(4)
                .maxPoolSize(8);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        return false;
    }

}

