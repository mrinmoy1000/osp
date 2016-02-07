package com.flamingos.osp.dto;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class FileUploadDTO implements Serializable {

	private static final Logger logger = Logger.getLogger(FileUploadDTO.class);
	private static final long serialVersionUID = 1L;

	private String filePath;
	private String result;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
