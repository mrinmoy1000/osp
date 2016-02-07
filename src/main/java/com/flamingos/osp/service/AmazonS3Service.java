package com.flamingos.osp.service;

import org.springframework.web.multipart.MultipartFile;

import com.flamingos.osp.exception.OSPBusinessException;

public interface AmazonS3Service {
	
	public  String handleFileUpload(MultipartFile file, int recordId) throws OSPBusinessException  ;
	
	public  void delete(String file,int recordId) throws OSPBusinessException  ;
}
