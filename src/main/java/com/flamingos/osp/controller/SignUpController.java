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
import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.SignUpService;
import com.flamingos.osp.util.AppConstants;
import org.apache.log4j.Logger;

@RestController
public class SignUpController {

    private static final Logger logger = Logger.getLogger(SignUpController.class);
    @Autowired
    private SignUpService signUpService;

    @Autowired
    private ConfigParamBean configParamBean;

    @RequestMapping(value = "/register", produces = "application/json", method = RequestMethod.POST,
            consumes = "application/json")
    public ResponseEntity<UserDTO> signupUser(@RequestBody UserBean userBean,
            HttpServletRequest request) throws Exception {
        logger.debug("Entrying SignupController >> signupUser() method");
        UserDTO userDto = new UserDTO();
        try {
            ConfigParamDTO oParamProfessional
                    = configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
                            AppConstants.PARAM_NAME_PROFESSIONAL);

            RoleBean oRoleProfessional = configParamBean.getRoleByName(AppConstants.ROLE_ADMINISTRATOR);
            userBean.setRecordType(oParamProfessional.getParameterid());
            userBean.setRoleId(oRoleProfessional.getRoleId());

            userDto = signUpService.createUser(userBean, request);
            if (null == userDto) {
                return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
            }

            return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);

        } catch (OSPBusinessException exp) {
            userDto.setReturnStatus(AppConstants.FAILURE);
            userDto.setReturnMessage(exp.getMessage());
            logger.error("Error in  creating user " + this.getClass(), exp);
            return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
        } finally {
            logger.debug("Exiting SignupController << signupUser() method");
        }

    }

    @RequestMapping(value = "/checkUser", produces = "application/json", method = RequestMethod.GET, consumes = "application/json")
    public ResponseEntity<UserDTO> checkUserName(@RequestBody UserBean userBean)
            throws Exception {
        logger.debug("Entrying SignupController >> checkUserName() method");
        UserDTO userDto = new UserDTO();
        try {
            userDto = signUpService.checkUserName(userBean);
            return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
        } catch (OSPBusinessException exp) {
            userDto.setReturnStatus(AppConstants.FAILURE);
            userDto.setReturnMessage(exp.getMessage());
            logger.error("Error in checking User name " + this.getClass(), exp);
            return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
        } finally {
            logger.debug("Exiting SignupController << checkUserName() method");
        }

    }

    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId) {
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    public ResponseEntity<String> deactivateUser(@PathVariable("userId") String userId) {
        return new ResponseEntity<String>("success", HttpStatus.CREATED);
    }
}
