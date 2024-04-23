package com.example.historydto;

import java.util.Date;

public class HistoryDTO {

	private String text;

	public HistoryDTO() {
		super();
	}
	public HistoryDTO(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
