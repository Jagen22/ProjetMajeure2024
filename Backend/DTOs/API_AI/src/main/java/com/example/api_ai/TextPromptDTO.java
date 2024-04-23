package com.example.api_ai;

public class TextPromptDTO {
	private String prompt;
	public TextPromptDTO() {
	}

	public TextPromptDTO(String prompt) {
		this.prompt = prompt;
	}

	public String getPrompt() {
		return prompt;
	}
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}
}
