package com.flamingos.osp.controller;

import com.flamingos.osp.constant.OSPConstant;
import com.flamingos.osp.exception.OspServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flamingos.osp.service.ProfessionalService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VerificationController {

	@Autowired
	ProfessionalService profService;
	private static final Logger logger = Logger.getLogger(ProfessionalController.class);
	@RequestMapping(value = "/verifyEmail", method = RequestMethod.GET)
	public ResponseEntity<String> verifyEmail(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			String successMessage = profService.verifyEmailDataAndUpdateStatus(	username, UUID, "email");
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (OspServiceException exp) {
			logger.debug("Error in  verify login"+this.getClass(),exp);
			return new ResponseEntity<String>(OSPConstant.ERROR, HttpStatus.OK);

		}

	}

	@RequestMapping(value = "/verifySms", method = RequestMethod.GET)
	public ResponseEntity<String> verifySMS(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			String successMessage = profService.verifyEmailDataAndUpdateStatus(username, UUID, "sms");
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (OspServiceException e) {
			logger.debug("Error in  verify sms"+this.getClass(),e);
			return new ResponseEntity<String>(OSPConstant.ERROR, HttpStatus.OK);
		}
		
	}

	@RequestMapping(value = "/verifyForgotPassword", method = RequestMethod.GET)
	public ResponseEntity<String> verifyForgotPassword(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			String successMessage = profService.verifyEmailDataAndUpdateStatus(username, UUID, "FUP");
			return new ResponseEntity<String>(successMessage, HttpStatus.OK);
		} catch (Exception e) {
			logger.debug("Error in  verify password"+this.getClass(),e);
			return new ResponseEntity<String>(OSPConstant.ERROR, HttpStatus.OK);
		}
		
	}

}
