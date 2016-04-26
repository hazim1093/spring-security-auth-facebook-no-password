package com.example.hazim1093.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by hazim on 4/26/16
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private FacebookAuthenticationFilter facebookAuthenticationFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// to allow CORS option calls
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().httpBasic().disable();
		http.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.addFilterBefore(facebookAuthenticationFilter, BasicAuthenticationFilter.class); // Apply Custom `AuthFilter` before basic authentication
	}

	/**
	 * Override SpringBoot's filter registration bean
	 * to avoid SpringBoot to load the filter automatically
	 * and to load it manually at a place we want
	 * e.g like before basic auth
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setEnabled(false);
		filterRegistrationBean.setFilter(facebookAuthenticationFilter);
		return filterRegistrationBean;
	}
}
