package com.example.historydto;

import java.util.Date;

public class CreateHistoryDTO {

	private String text;


	private Date date;

	public CreateHistoryDTO() {
		super();
	}
	public CreateHistoryDTO(String text, Date date) {
		super();
		this.text = text;
		this.date = date;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}


}
