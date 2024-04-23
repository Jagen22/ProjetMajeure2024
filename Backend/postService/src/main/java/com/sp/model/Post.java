package com.sp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Post {
	@Id
	@GeneratedValue
	private Integer id;

	private Integer idPrompt;
	private Integer idUser;

	@Column(length = 4096)
	private String prompt;

	public Post() {
	}

	public Post(Integer idPrompt, Integer idUer, String prompt) {
		this.idPrompt = idPrompt;
		this.idUser = idUer;
		this.prompt = prompt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdPrompt() {
		return idPrompt;
	}
	public void setIdPrompt(Integer idPrompt) {
		this.idPrompt = idPrompt;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}
