package com.example.historyservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.List;

@Entity
public class History {

	@Id
	@GeneratedValue
	private Integer id;
	private Date date;
	@Column(length = 4096)
	private String description;

	@Column(length = 8192)
	private String Activities;

	public History() {
	}

	public History(Date date, String description, String Activities) {
		this.date = date;
		this.description = description;
		this.Activities = Activities;
	}

	public Integer getId() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getActivities() {
		return Activities;
	}
	public void setActivities(String Activities) {
		this.Activities = Activities;
	}



}
