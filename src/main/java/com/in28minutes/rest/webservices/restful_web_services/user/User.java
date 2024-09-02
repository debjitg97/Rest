package com.in28minutes.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;


@Entity(name="user_details")
public class User {
	
	protected User()
	{}

	@Id
	private String userName;
	private LocalDate birthDate;
	private String password;
	
	public User(String userName, LocalDate birthDate, String password) {
		this.userName = userName;
		this.birthDate = birthDate;
		this.password = password;
	}
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Post> posts;	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", birthDate=" + birthDate + "]";
	}
}
