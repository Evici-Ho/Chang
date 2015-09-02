package com.dl.middleware.base.model;

import java.util.Date;

public class User {

	private Long userId;
	private String openId;
	private String contactName;
	private String contactPhone;
	private String password;
	private int post;
	private Date registerDate;
	private String undefined1;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getUndefined1() {
		return undefined1;
	}

	public void setUndefined1(String undefined1) {
		this.undefined1 = undefined1;
	}

}
