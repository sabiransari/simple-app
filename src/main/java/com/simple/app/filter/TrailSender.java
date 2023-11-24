package com.simple.app.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrailSender {
    private static final String QUEUE_NAME = "apiTrailQueue";

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    public void send(String message) {
        Message<String> msg = MessageBuilder.withPayload(message)
                .setHeader("sender", "cart-app")
                .build();
        queueMessagingTemplate.convertAndSend(QUEUE_NAME, msg);
        log.info("trail sent successfully");
    }
}
