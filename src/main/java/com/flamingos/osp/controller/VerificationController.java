package com.flamingos.osp.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.AppConstants;

@Controller
public class VerificationController {

  @Autowired
  ProfessionalService profService;
  private static final Logger logger = Logger.getLogger(VerificationController.class);

  @RequestMapping(value = "/verifyEmail", method = RequestMethod.GET)
  public ModelAndView verifyEmail(
      @RequestParam(value = "username", required = false) String username, @RequestParam(
          value = "UUID", required = false) String UUID) {
    logger.debug("Entrying VerficationController >> verifyEmail() method");
    try {
      UserDTO userDto = profService.verifyEmailDataAndUpdateStatus(username, UUID, "email");
      ModelAndView mav = new ModelAndView("linkSuccessPage");
      mav.addObject("user", userDto);
      return mav;

    } catch (OSPBusinessException exp) {
      logger.error("Error in  verify Email " + this.getClass(), exp);
      UserDTO userDto = new UserDTO();
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(exp.getErrorDescription());
      ModelAndView mav = new ModelAndView("errorLinkPage");
      mav.addObject("user", userDto);
      return mav;
    } finally {
      logger.debug("Exiting VerficationController << verifyEmail() method");
    }

  }

  @RequestMapping(value = "/verifySms", method = RequestMethod.GET)
  public ModelAndView verifySMS(
      @RequestParam(value = "username", required = false) String username, @RequestParam(
          value = "UUID", required = false) String UUID) {
    logger.debug("Entrying VerficationController >> verifySms() method");
    try {
      UserDTO userDto = profService.verifyEmailDataAndUpdateStatus(username, UUID, "sms");
      userDto.setReturnMessage("You have been Verified");
      ModelAndView mav = new ModelAndView("linkSuccessPage");
      mav.addObject("user", userDto);
      return mav;
    } catch (OSPBusinessException exp) {
      logger.error("Error in  verify sms" + this.getClass(), exp);
      UserDTO userDto = new UserDTO();
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(exp.getErrorDescription());
      ModelAndView mav = new ModelAndView("errorLinkPage");
      mav.addObject(userDto);
      return mav;
    } finally {
      logger.debug("Exiting VerficationController << verifySms() method");
    }

  }

  @RequestMapping(value = "/verifyForgotPassword", method = RequestMethod.GET)
  public ModelAndView verifyForgotPassword(
      @RequestParam(value = "username", required = false) String username, @RequestParam(
          value = "UUID", required = false) String UUID) {
    logger.debug("Entrying VerficationController >> verifyForgotPassword() method");
    try {
      UserDTO userDto = profService.verifyForgotPassword(username, UUID, "FUP");
      if (!userDto.getReturnStatus().equals("fail")) {
        ModelAndView mav = new ModelAndView("ResetPassword");
        mav.addObject("user", userDto);
        return mav;
      } else {
        ModelAndView mav = new ModelAndView("errorLinkPage");
        mav.addObject("user", userDto);
        return mav;
      }

    } catch (OSPBusinessException exp) {
      logger.error("Error in  verifyPassword link check  " + this.getClass(), exp);
      UserDTO userDto = new UserDTO();
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(exp.getErrorDescription());
      ModelAndView mav = new ModelAndView("errorLinkPage");
      mav.addObject(userDto);
      return mav;
    } finally {
      logger.debug("Exiting VerficationController << verifyForgotPassword() method");
    }

  }

  @RequestMapping(value = "/generateNewToken", method = RequestMethod.GET)
  public ResponseEntity<String> generateNewToken(
      @RequestParam(value = "username", required = false) String username, @RequestParam(
          value = "UUID", required = false) String UUID) {
    logger.debug("Entrying VerficationController >> generateNewToken() method");
    try {
      String successMessage = profService.verifyAndGenerateNewToken(username, UUID);
      return new ResponseEntity<String>(successMessage, HttpStatus.OK);
    } catch (OSPBusinessException e) {
      logger.error("Error in  generating new token" + this.getClass(), e);
      return new ResponseEntity<String>(AppConstants.ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    } finally {
      logger.debug("Exiting VerficationController << generateNewToken() method");
    }

  }

  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  public ModelAndView changePassword(@ModelAttribute UserBean userBean) {
    logger.debug("Entrying VerificationController >> changePassword() method");
    try {
      UserDTO userDto = profService.changePassword(userBean);
      ModelAndView mav = new ModelAndView("passwordSuccess");
      mav.addObject("user", userDto);
      return mav;
    } catch (OSPBusinessException e) {
      logger.error("Error in changing password " + this.getClass(), e);
      UserDTO userDto = new UserDTO();
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(e.getErrorDescription());
      ModelAndView mav = new ModelAndView("passwordError");
      mav.addObject(userDto);
      return mav;
    } finally {
      logger.debug("Exiting VerficationController << changePassword() method");
    }

  }

  @RequestMapping(value = "/verifyProf", method = RequestMethod.GET)
  public ModelAndView verfyProfessional(
      @RequestParam(value = "profId", required = false) String profID) {
    logger.debug("Entrying VerificationController >> verfyProfessional() method");
    try {
      UserDTO userDto = profService.verifyProfessional(profID);
      if (!userDto.getReturnStatus().equals(AppConstants.SUCCESS)) {
        ModelAndView mav = new ModelAndView("errorLinkPage");
        mav.addObject("user", userDto);
        return mav;
      } else {
        ModelAndView mav = new ModelAndView("RedirectSuccessPage");
        mav.addObject("user", userDto);
        return mav;
      }

    } catch (OSPBusinessException e) {
      logger.error("Error in changing password " + this.getClass(), e);
      UserDTO userDto = new UserDTO();
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(e.getErrorDescription());
      ModelAndView mav = new ModelAndView("errorLinkPage");
      mav.addObject(userDto);
      return mav;
    } finally {
      logger.debug("Exiting VerficationController << verfyProfessional() method");
    }

  }

}
