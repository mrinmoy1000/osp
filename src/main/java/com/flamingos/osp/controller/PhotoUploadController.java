package com.flamingos.osp.controller;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.flamingos.osp.dto.FileUploadDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.AmazonS3Service;

@RestController
@MultipartConfig(fileSizeThreshold = 5120000) // 5 MB
public class PhotoUploadController {

	@Autowired
	private AmazonS3Service amazonS3Service;

	private static final Logger logger = Logger.getLogger(PhotoUploadController.class);
	private static String CONTENT_TYPE = "image/jpeg";
	private static String CONTENT_TYPE_NOT_SUPPORTED_MSG = "Content type not supported";
	private static String UPLOAD_DONE_MSG = "Uploaded Succesfully";
	private static String UPLOAD_FAILED_MSG = "Upload Failed! Please try again";
	private static String DELETE_DONE_MSG = "Deleted Succesfully";
	private static String DELETE_FAILED_MSG = "Delete Failed! Please try again";

	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
	public FileUploadDTO handleFileUpload(@RequestParam(value = "uploadedFile", required = true) MultipartFile file,
			@RequestParam(value = "recordId", required = true) int recordId) throws IOException {
		FileUploadDTO fileUploadDTO=new FileUploadDTO();
		String returnMessage = UPLOAD_DONE_MSG;

		logger.debug("Entering PhotoUploadController >> handleFileUpload");
		if (!CONTENT_TYPE.equalsIgnoreCase(file.getContentType())){
			returnMessage = CONTENT_TYPE_NOT_SUPPORTED_MSG;
			logger.error ("Content type not supported"+file.getOriginalFilename()+" : Record Id="+recordId);
		}

		try {
			fileUploadDTO.setFilePath(amazonS3Service.handleFileUpload(file,recordId));
			//returnMessage = UPLOAD_DONE_MSG;
		} catch (OSPBusinessException e) {
			returnMessage = UPLOAD_FAILED_MSG;
		}finally{
			fileUploadDTO.setResult(returnMessage);			
		}

		logger.debug("Exiting PhotoUploadController << handleFileUpload");
		return fileUploadDTO;
	}
	
	@RequestMapping(value = "/deletePhoto", method = RequestMethod.POST)
	public String delete(@RequestParam(value = "fileName", required = true) String file,@RequestParam(value = "recordId", required = true) int recordId) throws IOException {

		String returnMessage = null;

		logger.debug("Entering PhotoUploadController >> delete");
		
		if (!StringUtils.isEmpty(file)){
			returnMessage = DELETE_FAILED_MSG;
			logger.error (DELETE_FAILED_MSG+file+" : Record Id="+recordId);
		}

		try {
			amazonS3Service.delete(file,recordId);
			returnMessage = DELETE_DONE_MSG;
		} catch (OSPBusinessException e) {
			returnMessage = DELETE_FAILED_MSG;
		}

		logger.debug("Exiting PhotoUploadController << delete");
		return returnMessage;
	}

}
