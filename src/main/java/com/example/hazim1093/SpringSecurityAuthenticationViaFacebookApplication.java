package com.example.hazim1093;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@SpringBootApplication
public class SpringSecurityAuthenticationViaFacebookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAuthenticationViaFacebookApplication.class, args);
	}

	// NOTE: The app with this ID should only be used for test purposes and not for production
	@Bean
	public FacebookConnectionFactory facebookConnectionFactory(){
		return new FacebookConnectionFactory("1709699072616518",
				"8412d5c13da9e32d3929b4c0de96bfe4");
	}
}
