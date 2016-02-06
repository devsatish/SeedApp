package com.project.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
	
	@Value("${app.mail.smtp}")
	private String host;
	
	@Value("${app.mail.port}")
	private int port;
	
	@Value("${app.mail.address}")
	private String address;
	
	@Value("${app.mail.password}")
	private String password;
	
	@Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        
        sender.setJavaMailProperties(properties);
		sender.setHost(host);
		sender.setPort(port);
		sender.setUsername(address);
		sender.setPassword(password);
		
        return sender;
    }

}
