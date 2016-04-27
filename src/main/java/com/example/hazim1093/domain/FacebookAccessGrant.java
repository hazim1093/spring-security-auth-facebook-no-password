package com.example.hazim1093.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.social.oauth2.AccessGrant;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by hazim on 4/18/16
 * Class for persisting the access grant object containing access details from Facebook.
 */
@Entity
@Table(name = "facebook_access_grant")
public class FacebookAccessGrant implements Serializable{
	@JsonIgnore
	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "access_token", nullable = false)
	private String accessToken;

	@Column(name = "scope")
	private String scope;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "expire_time")
	private Long expireTime;

	private FacebookAccessGrant(){

	}

	public FacebookAccessGrant(String accessToken) {
		this(accessToken, null, null, null);
	}

	public FacebookAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn) {
		this.accessToken = accessToken;
		this.scope = scope;
		this.refreshToken = refreshToken;
		this.expireTime = expiresIn;
	}

	public FacebookAccessGrant(AccessGrant accessGrant){
		this.accessToken = accessGrant.getAccessToken();
		this.scope = accessGrant.getScope();
		this.refreshToken = accessGrant.getScope();
		this.expireTime = accessGrant.getExpireTime();
	}

	public AccessGrant buildOAuth2AccessGrant(){
		return new AccessGrant(accessToken, scope, refreshToken, expireTime);
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}
}
