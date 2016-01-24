package com.flamingos.osp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.SignUpService;

@RestController
public class SignUpController {
	@Autowired
	private SignUpService signUpService;
	@RequestMapping(value ="/register",produces = "application/json", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<UserDTO> signupUser(@RequestBody UserBean userBean,
			HttpServletRequest request) throws Exception {
		UserDTO userDto = new UserDTO();
		try {
			userDto = signUpService.createUser(userBean, request);
			if (null == userDto)
				return new ResponseEntity<UserDTO>(userDto,HttpStatus.NOT_FOUND);

			return new ResponseEntity<UserDTO>(userDto, HttpStatus.CREATED);

		} catch (OspServiceException exp) {
                    userDto.setReturnStatus("fail");
                    userDto.setReturnMessage(exp.getMessage());
			return new ResponseEntity<UserDTO>(userDto, HttpStatus.NOT_FOUND);
		}

	}
	
	@RequestMapping(value ="/checkUser",produces = "application/json", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<UserDTO> checkUserName(@RequestBody UserBean userBean) throws Exception {
	 UserDTO userDto = new UserDTO();	
            try {
                   
			String returnMessage = signUpService.checkUserName(userBean);
                        userDto.setReturnStatus(returnMessage);
				return new ResponseEntity<UserDTO>(userDto,HttpStatus.FOUND);

		} catch (OspServiceException exp) {
                    userDto.setReturnStatus("fail");
                    userDto.setReturnMessage(exp.getMessage());
			return new ResponseEntity<UserDTO>(userDto, HttpStatus.NOT_FOUND);
		}

	}

	public ResponseEntity<String> deleteUser(
			@PathVariable("userId") String userId) {
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}

	public ResponseEntity<String> deactivateUser(
			@PathVariable("userId") String userId) {
		return new ResponseEntity<String>("success", HttpStatus.CREATED);
	}
}
