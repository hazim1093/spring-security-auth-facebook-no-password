package com.example.hazim1093.domain;

import javax.persistence.*;

/**
 * Created by hazim on 4/26/16
 */
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String email;
	private String facebookId;

	User(){

	}

	public User(String facebookId, String name, String email){
		this.facebookId = facebookId;
		this.name = name;
		this.email = email;
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
}
