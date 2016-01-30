package com.flamingos.osp.controller;

import java.util.Map;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.flamingos.osp.service.LoginService;
import com.flamingos.osp.util.AppConstants;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;
    private static final Logger logger = Logger.getLogger(ProfessionalController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> Login(@RequestBody UserBean userBean) {
        logger.debug("Entrying LoginCntroller >> login() method");
        UserDTO user = new UserDTO();
        try {
            user = loginService.login(userBean);
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        } catch (OSPBusinessException e) {
            user.setReturnStatus("fail");
            user.setReturnMessage(e.getErrorDescription());
            logger.error("Error in login " + this.getClass(), e);
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);

        } finally {
            logger.debug("Exiting LoginController << login() method");
        }

    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> forgotPasswordChecking(
            @ModelAttribute UserBean userBean, HttpServletRequest request,
            Map<String, Object> model) {
        logger.debug("Entrying LoginController >> forgotPasswordChecking() method");
        UserDTO user = new UserDTO();
        try {
            user = loginService.checkForUserAndSendLink(userBean, request);
            if (!user.getReturnStatus().equals(AppConstants.FAILURE)) {
                user.setReturnStatus(AppConstants.SUCCESS);
                user.setReturnMessage(AppConstants.VERIFICATION_LINK_NOTIFICATION);
            }
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        } catch (OSPBusinessException e) {
            logger.error("Error in forgot " + this.getClass(), e);
            user.setReturnStatus(AppConstants.FAILURE);
            user.setReturnMessage(e.getErrorDescription());
            return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
        } finally {
            logger.debug("Exiting SignUpController << forgotPasswordChecking() method");
        }

    }

}
