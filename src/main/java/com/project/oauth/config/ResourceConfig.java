package com.project.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Order(3)
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {
	
	@Value("${spring.remote.oauth.server}")
	private String remoteAuthServiceUrl;
	
	@Value("${spring.remote.oauth.client.id}")
	private String remoteAuthServiceClientId;
	
	@Value("${spring.remote.oauth.client.secret}")
	private String remoteAuthServiceClientSecret;

    @Bean    
    public ResourceServerTokenServices resourceTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId(remoteAuthServiceClientId);
        tokenServices.setClientSecret(remoteAuthServiceClientSecret);
        tokenServices.setCheckTokenEndpointUrl(remoteAuthServiceUrl);
        return tokenServices;
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {        
        resources.resourceId("settings-services").tokenServices(resourceTokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/api/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/api/**").access("#oauth2.hasScope('write')")
            .and()
                .headers().addHeaderWriter((request, response) -> {
                	response.addHeader("Access-Control-Allow-Origin", "*");
                    if (request.getMethod().equals("OPTIONS")) {
                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
                    }
                });
    }

}