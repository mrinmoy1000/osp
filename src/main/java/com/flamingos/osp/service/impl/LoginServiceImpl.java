package com.flamingos.osp.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.LoginDao;
import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.EmailService;
import com.flamingos.osp.service.LoginService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.EncoderDecoderUtil;

@Transactional
@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  LoginDao loginDao;
  @Autowired
  SignUpDao signUpDao;
  @Autowired
  EncoderDecoderUtil encDecUtil;
  @Autowired
  EmailService emailService;

  @Value("${fup.expire.time}")
  private int fupExpireTime;
  private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

  @Override
  public UserDTO login(UserBean loginBean) throws OSPBusinessException {
    String encryptedPassword = encDecUtil.getEncodedValue(loginBean.getPassword());
    loginBean.setPassword(encryptedPassword);
    try {
      UserDTO userDTO = loginDao.getUser(loginBean);
      if (userDTO != null) {
        if (!userDTO.getUserPass().equals(encryptedPassword)) {
          logger.debug("login authentication ended");
          userDTO = new UserDTO();
          userDTO.setReturnStatus(AppConstants.FAILURE);
          userDTO.setReturnMessage(AppConstants.LOGIN_FAILURE);
        } else {
          userDTO.setReturnStatus(AppConstants.SUCCESS);
          userDTO.setReturnMessage(AppConstants.LOGIN_SUCCESS);
          userDTO.setUserPass("");

        }
      } else {
        userDTO = new UserDTO();
        userDTO.setReturnStatus(AppConstants.FAILURE);
        userDTO.setReturnMessage(AppConstants.USER_NOT_FOUND);
      }

      return userDTO;
    } catch (Exception exp) {
      throw new OSPBusinessException(AppConstants.LOGIN_MODULE,
          AppConstants.LOGIN_EXCEPTION_ERRCODE, AppConstants.LOGIN_EXCEPTION_ERRDESC);
    }

  }

  @Override
  public UserDTO checkForUserAndSendLink(UserBean userBean, HttpServletRequest request)
      throws OSPBusinessException {
    logger.debug("user checking for link");
    try {
      UserDTO user = loginDao.checkForUser(userBean);
      if (user != null) {
        String Uuid = String.valueOf(UUID.randomUUID());
        userBean.setFupUUID(Uuid);
        userBean.setUser_id(user.getUserId());
        loginDao.addFUPAccessToken(userBean, fupExpireTime);
        String link = sendLinkForForgotPassword(userBean, request);
        logger.debug("Verfication link ,= " + link + " successfully sent");
      } else {
        user = new UserDTO();
        user.setReturnStatus("fail");
        user.setReturnMessage("User doesn't exist");
      }
      return user;
    } catch (Exception ex) {
      throw new OSPBusinessException(AppConstants.VERIFICATION_MODULE,
          AppConstants.FUP_TOKEN__ERRCODE, AppConstants.FUP_TOKEN_ERRDESC);

    }

  }


  public String sendLinkForForgotPassword(UserBean userBean, HttpServletRequest request)
      throws RuntimeException {
    String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());

    String linkTobeSend =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/verifyForgotPassword?username=" + encryptedUserName
            + "&UUID=" + userBean.getFupUUID();
    emailService.sendMail("EMAIL_VERIFY", userBean.getEmail(), linkTobeSend,
        "Forgot Password Link", userBean.getUserName());
    return linkTobeSend;
  }



}
