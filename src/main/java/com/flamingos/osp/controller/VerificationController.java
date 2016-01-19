package com.flamingos.osp.controller;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPConstants;
import com.flamingos.osp.exception.OspServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.service.ProfessionalService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class VerificationController {
	ProfessionalService profService;
	private static final Logger logger = Logger.getLogger(ProfessionalController.class);
	@RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
	public ResponseEntity<String> verifyEmail(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			UserBean user = new UserBean();
			user.setEmailUUID(UUID);
			String successMessage = profService.verifyEmailDataAndUpdateStatus(	username, user, "email");
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (OspServiceException exp) {
			logger.debug("Error in  verify login"+this.getClass(),exp);
			return new ResponseEntity<String>(OSPConstants.ERROR, HttpStatus.OK);

		}

	}

	@RequestMapping(value = "/verifySms", method = RequestMethod.POST)
	public ResponseEntity<String> verifySMS(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			UserBean user = new UserBean();
			user.setSmsUUID(UUID);
			String successMessage = profService.verifyEmailDataAndUpdateStatus(username, user, "sms");
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (OspServiceException e) {
			logger.debug("Error in  verify sms"+this.getClass(),e);
			return new ResponseEntity<String>(OSPConstants.ERROR, HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/verifyForgotPassword", method = RequestMethod.POST)
	public ResponseEntity<String> verifyForgotPassword(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			UserBean user = new UserBean();
			user.setFupUUID(UUID);
			String successMessage = profService.verifyEmailDataAndUpdateStatus(username, user, "FUP");
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Error in  verify password"+this.getClass(),e);
			return new ResponseEntity<String>(OSPConstants.ERROR, HttpStatus.OK);
		}
		
	}
	
	@RequestMapping(value = "/generateNewToken", method = RequestMethod.POST)
	public ResponseEntity<String> generateNewToken(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			String successMessage = profService.verifyAndGenerateNewToken(username,UUID);
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Error in  verify token"+this.getClass(),e);
			return new ResponseEntity<String>(OSPConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}


}
