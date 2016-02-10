package com.flamingos.osp.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.MasterDataBean;
import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.SignUpService;
import com.flamingos.osp.util.AppConstants;

@RestController
public class SignUpController {

  private static final Logger logger = Logger.getLogger(SignUpController.class);
  @Autowired
  private SignUpService signUpService;

  @Autowired
  private ConfigParamBean configParamBean;
  @Autowired
  private MasterDataBean masterDataBean;

  @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.POST,
      consumes = "application/json")
  public ResponseEntity<UserDTO> signupUser(@RequestBody UserBean userBean,
      HttpServletRequest request) throws Exception {
    logger.debug("Entrying SignupController >> signupUser() method");
    UserDTO userDto = new UserDTO();
    try {
      ConfigParamDTO oParamProfessional =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
              AppConstants.PARAM_NAME_PROFESSIONAL);

      RoleBean oRoleProfessional = masterDataBean.getRoleByName(AppConstants.ROLE_PROFESSIONAL);
      userBean.setRecordType(oParamProfessional.getParameterid());
      userBean.setRoleId(oRoleProfessional.getRoleId());

      userDto = signUpService.createUser(userBean, request);

      return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);

    } catch (OSPBusinessException exp) {
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(exp.getErrorDescription());
      logger.error("Error in  creating user " + this.getClass(), exp);
      return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    } finally {
      logger.debug("Exiting SignupController << signupUser() method");
    }

  }

  // http://localhost:8080/osp/checkUser?userName=raja
  @RequestMapping(value = "/checkUser", produces = "application/json", method = RequestMethod.GET,
      consumes = "application/json")
  public ResponseEntity<UserDTO> checkUserName(@RequestParam(value = "userName") String userName)
      throws Exception {
    logger.debug("Entrying SignupController >> checkUserName() method");

    UserDTO userDto = new UserDTO();
    try {
      userDto = signUpService.checkUserName(userName);
      return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    } catch (OSPBusinessException exp) {
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(exp.getMessage());
      // logger.error("Error in checking User name " + this.getClass(), exp);
      return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    } finally {
      logger.debug("Exiting SignupController << checkUserName() method");
    }

  }
  @RequestMapping(value = "/getUserDetails", produces = "application/json", method = RequestMethod.GET)
  public ResponseEntity<UserDTO> userDetailsByUserId(@RequestParam(value = "user_id") long user_id)
      throws Exception {
    logger.debug("Entrying SignupController >> userDetailsByUserId() method");

    UserDTO userDto = new UserDTO();
    try {
      userDto = signUpService.getUserDetailsByRecordId(user_id);
      return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    } catch (OSPBusinessException exp) {
      userDto.setReturnStatus(AppConstants.FAILURE);
      userDto.setReturnMessage(exp.getMessage());
      // logger.error("Error in checking User name " + this.getClass(), exp);
      return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
    } finally {
      logger.debug("Exiting SignupController << userDetailsByUserId() method");
    }

  }
  public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
    return new ResponseEntity<String>("success", HttpStatus.OK);
  }

  public ResponseEntity<String> deactivateUser(@PathVariable("userId") String userId) {
    return new ResponseEntity<String>("success", HttpStatus.CREATED);
  }
}
