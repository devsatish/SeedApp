package com.project.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Value("${app.mail.address}")
	private String address;

	@Autowired
	private JavaMailSender sender;
	
	public void sendEmail(String address, String subject, String content) throws MessagingException {
 
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(address);
		helper.setTo(address);
		
		helper.setSubject(subject);
		helper.setText(content);

		sender.send(message);
	}
	
	public void sendEmail(String subject, String content) throws MessagingException {
				 
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(address);
		helper.setTo(address);
		
		helper.setSubject(subject);
		helper.setText(content);

		sender.send(message);
	}

}
	