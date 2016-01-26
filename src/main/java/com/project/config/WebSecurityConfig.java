package com.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;

import com.project.auth.CustomAccessDeniedHandler;
import com.project.auth.CustomAuthenticationEntryPoint;
import com.project.auth.CustomAuthenticationFailureHandler;
import com.project.auth.CustomAuthenticationSuccessHandler;
import com.project.auth.CustomLogoutSuccessHandler;
import com.project.filter.CsrfHeaderFilter;
import com.project.service.CustomUserDetailsService;

@Order(1)
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/app/**", "/view/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/user/**").access("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
				.antMatchers("/connect/**", "/community/**", "/collection/**", "/registration/**", "/home/**", "/data/**", "/entity/**", "/403", "/").permitAll()
				.anyRequest().authenticated()
				.and()
				
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
				.and()
				
			.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)
				.and()
				
			.formLogin()
				.loginPage("/login")
				.successHandler(customAuthenticationSuccessHandler)
				.failureHandler(customAuthenticationFailureHandler)
				.and()
			
			.csrf()
				.csrfTokenRepository(csrfTokenRepository())
				.and()
				
			.logout()				
				.logoutUrl("/logout")
				.clearAuthentication(true)
				.invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID", "XSRF-TOKEN")
	            .logoutSuccessHandler(customLogoutSuccessHandler)
	            .and()
			
			.addFilterAfter(new CsrfHeaderFilter(), SessionManagementFilter.class);			
			
	}
	
	private CsrfTokenRepository csrfTokenRepository() {
	  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	  repository.setHeaderName("X-XSRF-TOKEN");
	  return repository;
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
        	.passwordEncoder(passwordEncoder);
    }
		
}
