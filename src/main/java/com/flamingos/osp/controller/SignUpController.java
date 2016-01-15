package com.flamingos.osp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.service.SignUpService;


@RestController
@RequestMapping("/register")
public class SignUpController {
   @Autowired
   SignUpService signUpService;

   @RequestMapping(produces="application/json",method = RequestMethod.POST,consumes="application/json")
   public ResponseEntity<String> signupUser(@RequestBody UserBean   userBean,HttpServletRequest request) throws Exception {
      String successMessage =  signUpService.createUser(userBean, request);
	
     if(!"success".equalsIgnoreCase(successMessage))
			return new ResponseEntity<String>(successMessage, HttpStatus.NOT_FOUND);
     
       return new ResponseEntity<String>(successMessage, HttpStatus.CREATED);
   }
    

public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) 
{
	 return new ResponseEntity<String>("success", HttpStatus.CREATED);
	 }
public ResponseEntity<String> deactivateUser(@PathVariable("userId") String userId)
{
	 return new ResponseEntity<String>("success", HttpStatus.CREATED);}
}
