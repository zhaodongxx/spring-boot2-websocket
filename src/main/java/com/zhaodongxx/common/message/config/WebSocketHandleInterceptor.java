package com.zhaodongxx.common.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;

/**
 * 输入消息拦截器
 *
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/5/7 11:43
 */
@Component
@Slf4j
public class WebSocketHandleInterceptor extends ChannelInterceptorAdapter {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 在消息被发送到通道之前调用。这允许在必要时修改消息。如果该方法返回null，那么实际发送调用就不会发生。
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        log.info(message.getHeaders().toString());
        log.info(message.getPayload().toString());


        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        log.info("【服务器】 -> 【客户端】 消息类型 " + accessor.getCommand().name());

        log.debug("preSend");

        if (StompCommand.CONNECT.equals(accessor.getCommand()) || StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            Principal username = accessor.getUser();
            if (StringUtils.isEmpty(username)) {
                return null;
            }
            // 绑定user
            accessor.setUser(username);
        }
        return message;
    }

    /**
     * 在发送调用后立即调用。布尔值参数表示该调用的返回值。
     *
     * @param message
     * @param channel
     * @param sent
     */
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        //do something
        log.debug("postSend");
    }

    /**
     * 在发送完成后调用，无论是否已经引发任何异常，从而允许适当的资源清理。
     * 请注意，只有preSend成功完成并返回一条消息，也就是说它没有返回null,才会调用该方法
     *
     * @param message
     * @param channel
     * @param sent
     * @param ex
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        log.debug("afterSendCompletion");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        if (StompCommand.SUBSCRIBE.equals(command)) {
            log.info(this.getClass().getCanonicalName() + " 订阅消息发送成功");
//            this.simpMessagingTemplate.convertAndSend("/topic/notice", "消息发送成功");
        }
        //如果用户断开连接
        if (StompCommand.DISCONNECT.equals(command)) {
            log.info(this.getClass().getCanonicalName() + "用户断开连接成功");
//            simpMessagingTemplate.convertAndSend("/topic/getResponse", "{'msg':'用户断开连接成功'}");
        }
        super.afterSendCompletion(message, channel, sent, ex);
    }

    /**
     * 调用receive后立即调用，并在实际检索消息之前调用。
     * 如果返回值为“false”，则不会检索任何消息。
     * 这仅适用于PollableChannels。
     *
     * @param channel
     * @return
     */
    @Override
    public boolean preReceive(MessageChannel channel) {
        log.info("preReceive");

        System.out.println(this.getClass().getCanonicalName() + " preReceive");
        return super.preReceive(channel);
    }


    /**
     * 在检索到Message之后但在将其返回给调用者之前立即调用。如有必要可以修改消息;null会中止进一步的拦截器调用。这仅适用于PollableChannels。
     * <p>
     * Invoked immediately after a Message has been retrieved but before it is returned to the caller.
     * The Message may be modified if necessary; null aborts further interceptor invocations.
     * This only applies to PollableChannels.
     *
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        log.info("postReceive");

        return message;
    }

    /**
     * 在完成接收后调用，无论是否已经引发任何异常，从而允许适当的资源清理。
     *
     * @param message
     * @param channel
     * @param ex
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        log.info("afterReceiveCompletion");

    }
}
