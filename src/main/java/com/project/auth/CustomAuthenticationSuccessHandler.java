package com.project.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.service.ClientTokenService;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {
	
	@Autowired
	private ClientTokenService clientTokenService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		clearAuthenticationAttributes(request);
		
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		OAuth2AccessToken token = clientTokenService.craftToken(userDetails);
		
		List<String> roles = new ArrayList<String>();
		
		userDetails.getAuthorities().stream().forEach(authority -> {			
			roles.add(authority.getAuthority());
		});
		
		Map<String, String> object = new HashMap<String, String>();
		
		object.put("user", userDetails.getUsername());
		object.put("role", roles.get(0));
		object.put("token", token.toString());
		
		JsonNode jsonNode = objectMapper.valueToTree(object);
		
		response.getWriter().write(jsonNode.toString());
		
	}
	
}
