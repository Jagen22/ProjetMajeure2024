package com.example.postdto;


import com.example.profiledto.ProfileDTO;

import java.io.Serializable;

public class PostDTO implements Serializable{
	private static final long serialVersionUID =1069270118228032176L ;
	private String prompt;
	private ProfileDTO profile;

	public PostDTO() {
	}
	public PostDTO(String prompt, ProfileDTO profile) {
		super();
		this.prompt = prompt;
		this.profile = profile;
	}

	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public ProfileDTO getProfile() {
		return profile;
	}
	public void setProfile(ProfileDTO profile) {
		this.profile = profile;
	}
}
