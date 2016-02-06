package com.project.model;

import java.util.Date;

public class DecodedToken {

	private Date date;
	private String content;
	private String type;
	
	public DecodedToken(Date date, String content, String type) {
		setDate(date);
		setContent(content);
		setType(type);
	}
	
	public DecodedToken(String[] values) {
		this(new Date(Long.valueOf(values[0])*1000), values[1], values[2]);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
