package com.flamingos.osp.bean;

public class TemplateBean {
	private int templateId;
	private String tempName;
	private int channelId;
	private int templateCatId;
	private int templateSubCatId;
	private int isEditable;
	private String tempFilePath;

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getTempFilePath() {
		return tempFilePath;
	}

	public void setTempFilePath(String tempFilePath) {
		this.tempFilePath = tempFilePath;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getTemplateCatId() {
		return templateCatId;
	}

	public void setTemplateCatId(int templateCatId) {
		this.templateCatId = templateCatId;
	}

	public int getTemplateSubCatId() {
		return templateSubCatId;
	}

	public void setTemplateSubCatId(int templateSubCatId) {
		this.templateSubCatId = templateSubCatId;
	}

	public int getIsEditable() {
		return isEditable;
	}

	public void setIsEditable(int isEditable) {
		this.isEditable = isEditable;
	}

}
