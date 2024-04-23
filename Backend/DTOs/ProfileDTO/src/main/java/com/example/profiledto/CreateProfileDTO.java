package com.example.profiledto;

import java.io.Serializable;
import java.util.List;

public class CreateProfileDTO implements Serializable {
	private static final long serialVersionUID = 1069270118228032176L;
	private String description_user;

	private List<String> ListParam;

	public CreateProfileDTO() {
	}
	public CreateProfileDTO(String description_user, List<String> ListParam) {
		this.description_user = description_user;
		this.ListParam = ListParam;
	}

	public String getDescription_user() {
		return description_user;
	}
	public void setDescription_user(String description_user) {
		this.description_user = description_user;
	}
	public List<String> getListParam() {
		return ListParam;
	}
	public void setListParam(List<String> ListParam) {
		this.ListParam = ListParam;
	}

}
