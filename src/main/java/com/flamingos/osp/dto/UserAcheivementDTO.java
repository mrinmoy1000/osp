package com.flamingos.osp.dto;

import java.io.Serializable;

public class UserAcheivementDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String year;
	private String description;
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
