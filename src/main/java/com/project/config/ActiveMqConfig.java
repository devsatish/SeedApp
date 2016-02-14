package com.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.SimpleMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.project.receiver.StormQueueReceiver;
import com.project.service.SkylineService;

@Configuration
public class ActiveMqConfig {
	
	@Autowired
	@Lazy
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	@Lazy
	private ObjectMapper objectMapper;
	
	@Autowired
	@Lazy
	private SkylineService skylineService;
	
	private static String destination = "storm.skyline.queue";

	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(new ActiveMQConnectionFactory("tcp://192.168.1.120:61616"));
	}

	@Bean
	public MessageListenerAdapter receiver() {
		return new MessageListenerAdapter(new StormQueueReceiver(skylineService, objectMapper, simpMessagingTemplate)) {
			{
				setDefaultListenerMethod("receiveMessage");
			}
		};
	}

	@Bean
	public SimpleMessageListenerContainer container(final MessageListenerAdapter messageListener, final ConnectionFactory connectionFactory) {
		return new SimpleMessageListenerContainer() {
			{
				setMessageListener(messageListener);
				setConnectionFactory(connectionFactory);
				setDestinationName(destination);
			}
		};
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
		return new JmsTemplate(connectionFactory);
	}

}
