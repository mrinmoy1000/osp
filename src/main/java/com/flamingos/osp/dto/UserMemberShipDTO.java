package com.flamingos.osp.dto;

import java.io.Serializable;

public class UserMemberShipDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String membershipNo;
	private String membershipType;
	private String membershipAuth;
	private String year;
	public String getMembershipNo() {
		return membershipNo;
	}
	public void setMembershipNo(String membershipNo) {
		this.membershipNo = membershipNo;
	}
	public String getMembershipType() {
		return membershipType;
	}
	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}
	public String getMembershipAuth() {
		return membershipAuth;
	}
	public void setMembershipAuth(String membershipAuth) {
		this.membershipAuth = membershipAuth;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	

}
