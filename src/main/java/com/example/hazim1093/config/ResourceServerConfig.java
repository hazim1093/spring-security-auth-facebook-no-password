package com.example.hazim1093.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Created by hazim on 3/7/16.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private CustomLogoutSuccessHandler customLogoutSuccessHandler;
	@Autowired
	private TokenStore tokenStore;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources)
			throws Exception {
		resources.resourceId("my-app").tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.logout()
			.logoutSuccessHandler(customLogoutSuccessHandler)
		.and()
			.csrf()
			.disable();

		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/*.js").permitAll()
				.antMatchers("/*.css").permitAll()
				.antMatchers("/api/auth/facebook").permitAll()
				.antMatchers("/api/code").permitAll()
				.antMatchers("/api/hello").permitAll()
				.antMatchers("/static/login.html").permitAll()
				.anyRequest().authenticated();
	}
}
