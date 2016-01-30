package com.flamingos.osp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.AdminService;
import com.flamingos.osp.util.AppConstants;

@RestController
public class AdminController {
	private static final Logger logger = Logger.getLogger(AdminController.class);
	@Autowired
	AdminService adminService;

	@RequestMapping(value = "/approveProfile", produces = "application/json", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity<String> approveProfile(@RequestBody OspProfessionalBean professionalBean,
			HttpServletRequest request) throws Exception {
		logger.debug(" Entering AdminController.approveProfile");
		String successMessage = null;
		try {
			successMessage = adminService.approveProfile(professionalBean, request);
		} catch (OSPBusinessException e) {
			logger.error(" Exception occured in AdminController.approveProfile" + e.getErrorDescription());
			successMessage = e.getErrorDescription();
		}
		logger.debug(" Exiting AdminController.approveProfile");
		return new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/profDetails", method = RequestMethod.GET)
	public ResponseEntity<OspProfessionalDTO> getProfessionalDetails(@RequestParam(value = "id") int id) {
		logger.debug(" Entering AdminController.getProfessionalDetails");
		OspProfessionalDTO prof = null;
		try {
			prof = adminService.professionalDetails(id);
			return new ResponseEntity<OspProfessionalDTO>(prof, HttpStatus.OK);
		} catch (OSPBusinessException e) {
			logger.error(" Exception occured in AdminController.getProfessionalDetails" + e.getMessage());
			prof = new OspProfessionalDTO();
			prof.setReturnStatus(AppConstants.FAILURE);
			prof.setReturnMessage(e.getMessage());
			return new ResponseEntity<OspProfessionalDTO>(prof, HttpStatus.OK);

		} finally {
			logger.debug(" Exiting AdminController.getProfessionalDetails");
		}

	}

	@RequestMapping(value = "/allProfDetails", method = RequestMethod.GET)
	public ResponseEntity<String> getAllProfessionalDetails() {
		List<OspProfessionalDTO> profList = null;
		try {
			profList = adminService.allProfessionalDetails();
			return new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (OSPBusinessException e) {
			logger.error(e);
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}

	}
}
