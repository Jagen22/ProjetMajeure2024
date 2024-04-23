package com.example.profiledto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileDTO implements Serializable {
	private static final long serialVersionUID = 1069270118228032176L;
	private Integer id;
	private Integer histId;
	private Integer promptId;
	
	private String description;
	private String imageUrl;
	private List<Integer> postId;

	public ProfileDTO() {
	}

	public ProfileDTO(Integer histId, Integer promptId) {
		super();
		this.histId = histId;
		this.promptId = promptId;
		this.postId = new ArrayList<Integer>();
	}
	public ProfileDTO(Integer histId,String prompt, String imageUrl){
		super();
		this.histId = histId;
		this.description = prompt;
		this.imageUrl=imageUrl;
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

	public List<Integer> getPostId(){
		return postId;
	}

	public void setPostId(List<Integer> postId){
		this.postId = postId;
	}

	public void addPost(Integer postId){
		this.postId.add(postId);
	}

	public Integer getHistId() {
		return histId;
	}

	public void setHistId(Integer histId) {
		this.histId = histId;
	}

	public Integer getPromptId() {
		return promptId;
	}

	public void setPromptId(Integer promptId) {
		this.promptId = promptId;
	}
}