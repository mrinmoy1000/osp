package com.flamingos.osp.controller;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspServiceException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.AppConstants;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VerificationController {
	@Autowired
	ProfessionalService profService;
	private static final Logger logger = Logger.getLogger(ProfessionalController.class);
	@RequestMapping(value = "/verifyEmail", method = RequestMethod.POST)
	public ModelAndView verifyEmail(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			UserDTO userDto = profService.verifyEmailDataAndUpdateStatus(username, UUID, "email");		
			ModelAndView mav = new ModelAndView("linkSuccessPage");
			mav.addObject("user",userDto);
			return mav;
			
		} catch (OSPBusinessException exp) {
			logger.error("Error in  verify login"+this.getClass(),exp);
			UserDTO userDto = new UserDTO();
			userDto.setReturnStatus(AppConstants.FAILURE);
			userDto.setReturnMessage(exp.getErrorDescription());
			ModelAndView mav = new ModelAndView("errorLinkPage");
			mav.addObject("user",userDto);
			return mav;

		}

	}

	@RequestMapping(value = "/verifySms", method = RequestMethod.POST)
	public ModelAndView verifySMS(@RequestParam(value = "username", required = false) String username,
		@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			UserDTO userDto = profService.verifyEmailDataAndUpdateStatus(username, UUID, "sms");
			userDto.setReturnMessage("You have been Verified");
			ModelAndView mav = new ModelAndView("linkSuccessPage");
			mav.addObject("user",userDto);
			return mav;
		} catch (OSPBusinessException exp) {
			logger.error("Error in  verify sms"+this.getClass(),exp);
			UserDTO userDto = new UserDTO();
			userDto.setReturnStatus(AppConstants.FAILURE);
			userDto.setReturnMessage(exp.getErrorDescription());
			ModelAndView mav = new ModelAndView("errorLinkPage");
			mav.addObject(userDto);
			return mav;
		}
		
	}

	@RequestMapping(value = "/verifyForgotPassword", method = RequestMethod.POST)
	public ModelAndView verifyForgotPassword(
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "UUID", required = false) String UUID) {
		try {
			UserDTO userDto = profService.verifyForgotPassword(username, UUID, "FUP");
			if(!userDto.getReturnStatus().equals("fail"))
			{
			ModelAndView mav = new ModelAndView("ResetPassword");
			mav.addObject("user",userDto);
			return mav;
			}else
			{
				ModelAndView mav = new ModelAndView("errorLinkPage");
				mav.addObject("user",userDto);
				return mav;
			}
			
	} catch (OSPBusinessException exp) {
			logger.error("Error in  verify password"+this.getClass(),exp);
			UserDTO userDto = new UserDTO();
			userDto.setReturnStatus(AppConstants.FAILURE);
			userDto.setReturnMessage(exp.getErrorDescription());
			ModelAndView mav = new ModelAndView("errorLinkPage");
			mav.addObject(userDto);
			return mav;
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
			logger.error("Error in  verify token"+this.getClass(),e);
		return new ResponseEntity<String>(AppConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public ModelAndView changePassword(@ModelAttribute UserBean userBean) {
             logger.debug("Entrying Forgot Password");
         try {
        	 UserDTO userDto = profService.changePassword(userBean);
        	 ModelAndView mav = new ModelAndView("passwordSuccess");
 			 mav.addObject("user",userDto);
 			return mav;
		} catch (OSPBusinessException e) {
			 logger.error(e);
			 UserDTO userDto = new UserDTO();
			 userDto.setReturnStatus(AppConstants.FAILURE);
			 userDto.setReturnMessage(e.getErrorDescription());
			 ModelAndView mav = new ModelAndView("passwordError");
	 			mav.addObject(userDto);
	 			return mav;
		}
        
    }

}
