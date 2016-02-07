package com.flamingos.osp.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.S3Util;

@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {
	
	public static final String AWS_S3_BUCKET = "ospprofilephoto";
	public static final String FILE_NULL = "Uploading File is NULL or empty";
	public static final String DELETE_FAILED = "Delete File failed :Cause filename is Null or empty";
	
	
	public  String handleFileUpload(MultipartFile uploadFilePart,int recordId) throws OSPBusinessException  {
		
		try{
	        if (uploadFilePart != null) {
	            S3Util S3Util=new S3Util(AWS_S3_BUCKET,multipartToFile( uploadFilePart));
	      
	        	S3Util.save();
	        	return S3Util.getUrl().toString();
	       
	        }
	        else {
	        	throw new OSPBusinessException(AppConstants.PHOTO_UPLOAD_MODULE,
	        	          AppConstants.UPLOAD_PROFILE_PIC_MODULE_EXCEPTION_ERRCODE,
	        	          FILE_NULL);	           
	        }
	        
		} 
		catch (Exception e) {
			throw new OSPBusinessException(AppConstants.PHOTO_UPLOAD_MODULE,
      	          AppConstants.UPLOAD_PROFILE_PIC_MODULE_EXCEPTION_ERRCODE,
      	        AppConstants.PHOTO_UPLOAD_MODULE_EXCEPTION_ERRDESC,e);	
		}
		
	}
	
	
	private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException 
	{
	        File convFile = new File( multipart.getOriginalFilename());
	        multipart.transferTo(convFile);
	        return convFile;
	}
	
public  void delete(String fileName,int recordId) throws OSPBusinessException  {
		
		try{
	        if (!StringUtils.isEmpty(fileName) ) {
	            S3Util S3Util=new S3Util(AWS_S3_BUCKET,fileName);	      
	        	S3Util.delete();	       
	        }
	        else {
	        	throw new OSPBusinessException(AppConstants.PHOTO_UPLOAD_MODULE,AppConstants.UPLOAD_PROFILE_PIC_MODULE_EXCEPTION_ERRCODE, DELETE_FAILED);	           
	        }
	        
		} 
		catch (Exception e) {
			throw new OSPBusinessException(AppConstants.PHOTO_UPLOAD_MODULE,
      	          AppConstants.UPLOAD_PROFILE_PIC_MODULE_EXCEPTION_ERRCODE,
      	        DELETE_FAILED,e);	
		}
		
	}
	
}
