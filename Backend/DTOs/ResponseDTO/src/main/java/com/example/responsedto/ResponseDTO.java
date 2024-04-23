package com.example.responsedto;

public class ResponseDTO {
	private String prompt;
	private String image_url;

	public ResponseDTO() {
	}
	public ResponseDTO(String prompt, String image_url) {
		this.prompt = prompt;
		this.image_url = image_url;
	}

	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}


}
