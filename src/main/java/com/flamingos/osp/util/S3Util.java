package com.flamingos.osp.util;



import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

public class S3Util {

	public static final Logger logger = Logger.getLogger(S3Util.class);

	public S3Util(String bucket, File file) {
		id = UUID.randomUUID();
		this.bucket = bucket;
		this.file = file;
		this.name = file.getName();
	}
	
	public S3Util(String bucket, String file) {
		this.bucket = bucket;
		this.name = file;
	}

	public static final String AWS_ACCESS_KEY = "AKIAJ2ML4OBRKB25LBBA";
	public static final String AWS_SECRET_KEY = "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";

	private UUID id;

	private String bucket;

	private String name;

	private File file;

	public URL getUrl() throws MalformedURLException {
		return new URL("https://s3-ap-southeast-1.amazonaws.com/" + bucket + "/" + getActualFileName());
	}

	private String getActualFileName() {
		return id + "/" + name;
	}

	private String DELETE_ERROR_MSG = "Could not delete because amazonS3 was null";
	private String SAVE_ERROR_MSG = "Could not save because amazonS3 was null";

	// not handling exception here service level will do that
	public void save() {
	
			AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, getActualFileName(), file);
			putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead); // public for all
			PutObjectResult result = s3Client.putObject(putObjectRequest);
			logger.info("Uploaded file successfull. filename:" + getActualFileName());
		

	}

	public void delete() {
	
			AmazonS3 s3Client = new AmazonS3Client(new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY));
			s3Client.deleteObject(new DeleteObjectRequest(bucket, name));
		
	}
}
