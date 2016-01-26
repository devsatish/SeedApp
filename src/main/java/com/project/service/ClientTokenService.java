package com.project.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.stereotype.Service;

import com.project.oauth.model.OAuthClientDetails;
import com.project.oauth.token.CustomTokenGranter;

@Service
public class ClientTokenService {
	
	private static final String GRANT_TYPE = "client_credentials";
	
	@Autowired
	private JdbcClientDetailsService clientDetailsService;

	@Autowired
	private DefaultOAuth2RequestFactory oAuth2RequestFactory;

	@Autowired
	private CustomTokenGranter tokenGranter;

	public OAuth2AccessToken craftToken(UserDetails userDetails) {
		
		String clientId = userDetails.getUsername(), clientSecret = userDetails.getPassword();
		
		ClientDetails clientDetails = new OAuthClientDetails(clientId, "settings-services", clientSecret, "read,write", GRANT_TYPE, "", "ROLE_USER", 900, 1800, "", "false");

		try {
			clientDetailsService.addClientDetails(clientDetails);
			//System.out.println("Client details created");
		} catch (ClientAlreadyExistsException caee) {
			//System.out.println("Client already exists.");
		}

		ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client-id", clientId);
		parameters.put("client-secret", clientSecret);

		TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);

		OAuth2AccessToken token = tokenGranter.grant(GRANT_TYPE, tokenRequest);

		Map<String, Object> additionalInformation = token.getAdditionalInformation();

		additionalInformation.put("easter egg", "Hello, World!");

		((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionalInformation);
		
		return token;
	}
	
}
