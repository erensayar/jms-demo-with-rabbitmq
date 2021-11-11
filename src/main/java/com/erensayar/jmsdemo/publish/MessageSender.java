package com.erensayar.jmsdemo.publish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.TextMessage;

@Service
public class MessageSender {

    @Autowired
    ConnectionFactory connectionFactory;

    @Scheduled(fixedRate = 1000)
    public void rpcWithSpringJmsTemplate() throws Exception {
        Connection clientConnection = connectionFactory.createConnection();
        clientConnection.start();
        String messageContent = "Selam Canım Ben Amcanım";

        JmsTemplate tpl = new JmsTemplate(connectionFactory);
        tpl.setReceiveTimeout(2000);
        tpl.send("DemoQueue", session -> {
            TextMessage message = session.createTextMessage(messageContent);
            message.setJMSCorrelationID(messageContent);
            return message;
        });
    }
}