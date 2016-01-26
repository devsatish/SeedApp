package com.project.oauth.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.oauth.model.OAuthClientDetails;
import com.project.oauth.token.CustomTokenGranter;

@RestController
public class Token {

	private static final String GRANT_TYPE = "client_credentials";

	@Autowired
	private JdbcClientDetailsService clientDetailsService;

	@Autowired
	private DefaultOAuth2RequestFactory oAuth2RequestFactory;

	@Autowired
	private CustomTokenGranter tokenGranter;

	@RequestMapping(value = "/token", method = RequestMethod.GET)
	public OAuth2AccessToken main(@RequestHeader() Map<String, String> headers) {

		// should query database, insert if 0 result set else compare, update if different

		String clientId = "null", clientSecret = "null", clientEmail = "null";

		Iterator<Map.Entry<String, String>> i = headers.entrySet().iterator();
		while (i.hasNext()) {
			String key = i.next().getKey();
			if ("client-id".equals(key))
				clientId = headers.get(key);
			if ("client-secret".equals(key))
				clientSecret = headers.get(key);
			if ("client-email".equals(key))
				clientEmail = headers.get(key);
		}

		ClientDetails clientDetails = new OAuthClientDetails(clientId, "settings-services", clientSecret, "read,write", GRANT_TYPE, "", "ROLE_USER", 900, 1800, "", "false");

		if ("null".equals(clientId) || "null".equals(clientSecret)) {
			System.out.println("Client not authenticated.");
			return null;
		}

		try {
			clientDetailsService.addClientDetails(clientDetails);
			System.out.println("Client details created");
		} catch (ClientAlreadyExistsException caee) {
			System.out.println(
					"Client already exists. Modify to check if client exists and compare details with shibb payload.");
		}

		ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("client-id", clientId);
		parameters.put("client-secret", clientSecret);
		parameters.put("client-email", clientEmail);

		TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(parameters, authenticatedClient);

		OAuth2AccessToken token = tokenGranter.grant(GRANT_TYPE, tokenRequest);

		Map<String, Object> additionalInformation = token.getAdditionalInformation();

		additionalInformation.put("email", clientEmail);

		((DefaultOAuth2AccessToken) token).setAdditionalInformation(additionalInformation);

		return token;
	}

}