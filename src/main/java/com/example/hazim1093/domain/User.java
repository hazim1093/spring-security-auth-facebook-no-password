package com.example.hazim1093.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by hazim on 4/26/16
 */
@Entity
@Table(name = "user")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String email;
	private String facebookId;

	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private FacebookAccessGrant fbAccessGrant;

	User(){

	}

	public User(String facebookId, String name, String email, FacebookAccessGrant fbAccessGrant){
		this.facebookId = facebookId;
		this.name = name;
		this.email = email;
		this.fbAccessGrant = fbAccessGrant;
	}
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public FacebookAccessGrant getFbAccessGrant() {
		return fbAccessGrant;
	}

	/**
	 ***********************  User Detail Methods *******************
	 * Implemented for compatibility with spring security oauth
	 * i.e. to use Participant as principal
	 **/
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		throw new UnsupportedOperationException("Method getPassword is not supported for user");
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return facebookId;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
