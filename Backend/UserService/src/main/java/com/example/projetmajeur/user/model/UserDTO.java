package com.example.projetmajeur.user.model;

public class UserDTO {
	private Integer id;
	private String login;
	private String pwd;
	private String lastName;
	private String firstName;
	private String email;
	
	public UserDTO() {
	}

	public UserDTO(UserModel user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.pwd = user.getPwd();
		this.lastName = user.getLastName();
		this.firstName = user.getFirstName();
		this.email = user.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
