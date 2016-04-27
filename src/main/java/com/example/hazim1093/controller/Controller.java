package com.example.hazim1093.controller;

import com.example.hazim1093.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by hazim on 4/26/16
 */
@RestController
@RequestMapping("api")
public class Controller {
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	static String REDIRECT_URI = "/api/code";
	static String CLIENT_ID = "clientId";

	@Autowired
	private FacebookConnectionFactory facebookConnectionFactory;

	/**
	 * REST call without any security
	 */
	@RequestMapping("/hello")
	public String hello(){
		return "Hello from the other side";
	}

	@RequestMapping("/hello/secure")
	public String helloSecure(Principal principal){
		User user = (User)((OAuth2Authentication) principal).getUserAuthentication().getPrincipal();
		return "Hello from " + user.getName();
	}

	@RequestMapping("/me")
	public User me(Principal principal){
		User user = (User)((OAuth2Authentication) principal).getUserAuthentication().getPrincipal();
		return user;
	}

	@RequestMapping("/code")
	public String code(@RequestParam String code){
		return code;
	}

	@RequestMapping("/auth/facebook")
	public String authFacebook(@RequestParam String code, HttpServletRequest request){
		OAuth2Operations oauthOperations = facebookConnectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, "http://localhost:8080/", null);

		//requesting local OAuth Authorization server
		String url = "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + "/oauth/authorize?" +
				"client_id=" + CLIENT_ID +
				"&response_type=code" +
				"&redirect_uri=" + REDIRECT_URI;

		//headers
		HttpHeaders headers = new HttpHeaders();
		headers.set("fbToken", accessGrant.getAccessToken());
		HttpEntity entity = new HttpEntity(headers);

		log.info("Sending authorize request to internal OAuth Server...");
		RestTemplate restTemplate = new RestTemplate();
		//TODO: handle errors of this call
		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		if(responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null){
			log.info("Successfully retrieved authorization code.");
			return responseEntity.getBody();
		}
		else{
			log.error(responseEntity.toString());
			throw new InternalServerErrorException("/oauth/authorize", "Unable to receive authorization code.");
		}
	}
}
