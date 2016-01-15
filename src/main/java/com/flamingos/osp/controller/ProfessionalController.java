package com.flamingos.osp.controller;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.service.ProfessionalService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.apache.log4j.Logger;

@RestController
@RequestMapping("/professional")
public class ProfessionalController {

    private static final Logger logger = Logger.getLogger(ProfessionalController.class);
    @Autowired
    ProfessionalService profService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> createUser(@RequestBody UserBean userBean, HttpServletRequest request) {
        logger.debug("Entring professional controller");
        String successMessage =null;
      
     
        logger.info("Exiting professional controller");
        return new ResponseEntity<String>(successMessage, HttpStatus.OK);

    }

}
