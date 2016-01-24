package com.flamingos.osp.controller;

import java.util.Map;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPConstants;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flamingos.osp.service.LoginService;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
@Autowired
    LoginService loginService;
private static final Logger logger = Logger.getLogger(ProfessionalController.class);
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<UserDTO> Login(@RequestBody UserBean userBean) {
        logger.debug("Entrying login");
         UserDTO user = new UserDTO();
        try {
              user =loginService.login(userBean);
              logger.debug("Exiting login");
        return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        } catch (Exception e) {
            user.setReturnStatus("fail");
            user.setReturnMessage(e.getMessage());
            return new ResponseEntity<UserDTO>(user, HttpStatus.NOT_FOUND);
            
        }

       
    }

    
    @RequestMapping(value = "/forgotPassword",method = RequestMethod.POST)
    public ResponseEntity<String> forgotPasswordChecking(@ModelAttribute UserBean userBean,HttpServletRequest request, Map<String, Object> model) {
             logger.debug("Entrying Forgot Password");
         try {
        	 String userMessage = loginService.checkForUserAndSendLink(userBean,request);
             logger.debug("Exiting Forgot Password");
            return new ResponseEntity<String>("check your email", HttpStatus.OK);
		} catch (OspServiceException e) {
			 logger.debug(e);
			 return new ResponseEntity<String>(OSPConstants.ERROR, HttpStatus.OK);
		}
        
    }
    
    

}
