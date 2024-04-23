package com.example.api_ai;

public class ImagePromptDTO {
	private String prompt;
	private int image_number;
	public ImagePromptDTO() {
	}

	public ImagePromptDTO(String prompt, int image_number) {
		this.prompt = prompt;
		this.image_number = image_number;
	}

	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
	public int getImage_number() {
		return image_number;
	}
	public void setImage_number(int image_number) {
		this.image_number = image_number;
	}

}
