package com.flamingos.osp.controller;

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
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.ProfessionalService;

@RestController
public class ProfessionalController {

    private static final Logger logger = Logger.getLogger(ProfessionalController.class);

    @Autowired
    ProfessionalService profService;
    
    @RequestMapping(value = "/addProfessional",produces = "application/json", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> addProfile(@RequestBody OspProfessionalBean professionalBean,
			HttpServletRequest request) throws Exception {
		String successMessage = profService.addProfile(professionalBean, request);		

		return new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
	}
    
   
    
    

}
