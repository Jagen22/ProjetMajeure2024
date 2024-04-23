package com.projetmajeure.influgenerator.profile.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class Profile {

	@Id
	@GeneratedValue
	private Integer id;
	private Integer histId;

	@Column(length = 4096)
	private String description;
	private String imageUrl;
	private String listPostId;
	
	
	public Profile() {}
	
	public Profile(Integer histId, String description, String imageUrl) {
		super();
		this.histId = histId;
		this.description=description;
		this.imageUrl=imageUrl;
		this.listPostId = "";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getListPostId(){
		return listPostId;
	}

	public void setListPostId(String postId){
		this.listPostId = postId;
	}

	public void addPost(Integer postId){
		String postIdString = postId.toString();
		this.listPostId = this.listPostId + "," + postIdString ;
	}

	public Integer getHistId() {
		return histId;
	}

	public void setHistId(Integer histId) {
		this.histId = histId;
	}
}