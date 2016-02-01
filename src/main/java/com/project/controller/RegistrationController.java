package com.project.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.exception.AbstractException;
import com.project.model.DecodedToken;
import com.project.model.Group;
import com.project.model.User;
import com.project.model.repo.GroupRepo;
import com.project.model.repo.UserRepo;
import com.project.service.CryptoService;
import com.project.service.EmailService;
import com.project.service.IndexService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
	
	private static final String TOKEN_TYPE = "REGISTRATION";
	
	@Value("${info.build.version}")
	private String version;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private GroupRepo groupRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private IndexService indexService;
	
	@Autowired
	private CryptoService cryptoService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/begin")
	public String register(HttpServletRequest request, @RequestBody Map<String, String> data) {		

		User user = new User(data.get("username"), passwordEncoder.encode(data.get("password")), "ROLE_USER", false);
		
		String email = data.get("email");
		
		Map<String, String> profile = new HashMap<String, String>();
		profile.put("email", data.get("email"));
		profile.put("firstName", data.get("firstName"));
		profile.put("lastName", data.get("lastName"));
		
		user.setProfile(profile);
		
		try {
			user = userRepo.save(user);
		}
		catch(DataIntegrityViolationException e) {
			throw new RegistrationException("A registration has already begun with email " + email);
		}
		
		String token;
		
		try {
			token = cryptoService.generateToken(user.getUsername(), TOKEN_TYPE);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException e1) {
			throw new RegistrationException("Could not generate token!");
		}
		
		URL requestUrl;
		
		try {
			requestUrl = new URL(request.getRequestURL().toString());
		} catch (MalformedURLException e1) {
			throw new RegistrationException("Could not craft link!");
		}
		
		String link = requestUrl.getProtocol() + "://" + requestUrl.getHost() + ":" + requestUrl.getPort() + request.getServletContext().getContextPath() + "/registration/" + token;
		
		try {
			emailService.sendEmail(email, "Ascension Registration", "Please follow link to complete registration:\n\n" + link);
		} catch (MessagingException e) {
			throw new RegistrationException("Could not send email to " + email + "!");
		}
		
		return "SUCCESS";
	}
	
	@Transactional
	@RequestMapping(value = "/{encodedToken}")
	public ResponseEntity<String> confirm(HttpServletRequest request, @PathVariable String encodedToken) {
		DecodedToken token;
		
		try {
			token = cryptoService.validateToken(encodedToken, TOKEN_TYPE);			
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e) {
			throw new RegistrationException("Bad token!");
		}
		
		String username = token.getContent();
		
		User user;
		
		if((user = userRepo.findByUsername(username)) == null) {
			throw new RegistrationException("Username " + username + " not found!");
		}
		
		user.setActive(true);
		user.setRole("ROLE_USER");
				
		user = userRepo.save(user);
		
		Group everyone = groupRepo.findByName("Everyone");
		everyone.addUser(user);
		groupRepo.save(everyone);
				
		return new ResponseEntity<String>(indexService.getIndex(), new HttpHeaders(), HttpStatus.ACCEPTED);
	}
	
	@ResponseBody
	public class RegistrationException extends AbstractException {
		private static final long serialVersionUID = 1L;
		public RegistrationException(String message) {
			super(message);
		}
	}
		
}
