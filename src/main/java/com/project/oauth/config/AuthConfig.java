package com.project.oauth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.project.oauth.token.CustomTokenGranter;

@Order(2)
@Configuration
@EnableAuthorizationServer
public class AuthConfig extends AuthorizationServerConfigurerAdapter {
	
	private static final String GRANT_TYPE = "client_credentials";
	
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired
	private JwtTokenStore jwtTokenStore;
	
	@Autowired
	private DataSource dataSource;
	
    @Bean
    public JdbcClientDetailsService clientDetailsService() {
        return new JdbcClientDetailsService(dataSource);
    }
        
	@Bean 
    public JwtAccessTokenConverter tokenEnhancer() {
    	return new JwtAccessTokenConverter();
    }
    
    @Bean
    public JwtTokenStore tokenStore() {
    	return new JwtTokenStore(jwtAccessTokenConverter);
    } 
    
    @Bean
    public TokenApprovalStore tokenApprovalStore() {
    	TokenApprovalStore tokenApprovalStore = new TokenApprovalStore();
    	tokenApprovalStore.setTokenStore(jwtTokenStore);
        return tokenApprovalStore;
    }
             
    @Bean
    @Primary
    public AuthorizationServerTokenServices authorizationTokenServices() { 
    	DefaultTokenServices tokenServices = new DefaultTokenServices(); 
    	tokenServices.setTokenStore(jwtTokenStore); 
    	tokenServices.setTokenEnhancer(jwtAccessTokenConverter); 
    	tokenServices.setSupportRefreshToken(true);
    	tokenServices.setClientDetailsService(clientDetailsService()); 
    	return tokenServices; 
    } 
    
    @Bean
    public DefaultOAuth2RequestFactory oAuth2RequestFactory() {
    	return new DefaultOAuth2RequestFactory(clientDetailsService());
    }
    
    @Bean
    public CustomTokenGranter tokenGranter() {
    	return new CustomTokenGranter(authorizationTokenServices(), clientDetailsService(), oAuth2RequestFactory(), GRANT_TYPE);
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {    	
        clients.withClientDetails(clientDetailsService());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    	oauthServer.tokenKeyAccess("hasAuthority('ROLE_ADMIN')").checkTokenAccess("hasAuthority('ROLE_ADMIN')");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	endpoints.approvalStore(tokenApprovalStore())
    			 .tokenStore(jwtTokenStore)
    			 .tokenServices(authorizationTokenServices())
    			 .accessTokenConverter(jwtAccessTokenConverter);
    }
    
}
