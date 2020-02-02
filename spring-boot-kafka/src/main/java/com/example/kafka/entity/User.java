package com.example.kafka.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class User {

	
	private Long id;
	
	private String userName;
	
	private String eMail;
	
	

	public User() {
	}
	
	

	public User(Long id, String userName, String eMail) {
		this.id = id;
		this.userName = userName;
		this.eMail = eMail;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	public String toString() {
		return String.format("{ \n id: %d, \n username: %s, \n eMail: %s \n}", getId(),getUserName(),geteMail());
	}
}
