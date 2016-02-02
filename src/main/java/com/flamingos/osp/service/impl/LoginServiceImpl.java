package com.flamingos.osp.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.LoginDAO;
import com.flamingos.osp.dao.SignUpDAO;
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
  LoginDAO loginDao;
  @Autowired
  SignUpDAO signUpDao;
  @Autowired
  EncoderDecoderUtil encDecUtil;
  @Autowired
  EmailService emailService;

  @Value("${fup.expire.time}")
  private int fupExpireTime;
  private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

  @Override
  public UserDTO login(UserBean loginBean) throws OSPBusinessException {
    logger.debug("Entrying LoginService >> login() method");
    String encryptedPassword = encDecUtil.getEncodedValue(loginBean.getPassword());
    loginBean.setPassword(encryptedPassword);
    try {
      UserDTO userDTO = loginDao.getUser(loginBean);
      if (userDTO != null) {
        if (!userDTO.getUserPass().equals(encryptedPassword)) {
          userDTO = new UserDTO();
          userDTO.setReturnStatus(AppConstants.FAILURE);
          userDTO.setReturnMessage(AppConstants.LOGIN_FAILURE);
        } else {
          UserDTO profUser = signUpDao.checkForProfessionalRecordId(loginBean);
          userDTO.setReturnStatus(AppConstants.SUCCESS);
          if (profUser.getUserId() != 0) {
            userDTO.setReturnMessage(AppConstants.VIEW_PROF_PROFILE);
            userDTO.setUserId(profUser.getUserId());
          } else {
            userDTO.setReturnMessage(AppConstants.ADD_PROF_PROFILE);
          }

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
          AppConstants.LOGIN_EXCEPTION_ERRCODE, AppConstants.LOGIN_EXCEPTION_ERRDESC, exp);
    } finally {
      logger.debug("Entrying LoginService << login() method");
    }
  }

	@Override
	public UserDTO checkForUserAndSendLink(UserBean userBean,
			HttpServletRequest request) throws OSPBusinessException {
		logger.debug("Entrying LoginService >> checkForUserAndSendLink() method");
		try {
			UserDTO user = loginDao.checkForUser(userBean);
			if (user != null) {
				String Uuid = String.valueOf(UUID.randomUUID());
				userBean.setFupUUID(Uuid);
				userBean.setUser_id(user.getUserId());
				if (user.getEmail().equals(userBean.getEmail())) {
					loginDao.addFUPAccessToken(userBean, fupExpireTime);
					String link = sendLinkForForgotPassword(userBean, request);
					logger.debug("Verfication link =  " + link
							+ " successfully sent");

					if (link.equals(AppConstants.SUCCESS)) {
						user.setReturnStatus(AppConstants.SUCCESS);
						user.setReturnMessage(AppConstants.VERIFICATION_LINK_NOTIFICATION);
					} else {
						user.setReturnStatus(AppConstants.FAILURE);
						user.setReturnMessage(AppConstants.FUP_TOKEN_ERRDESC);
					}

				} else {

					user.setReturnStatus(AppConstants.FAILURE);
					user.setReturnMessage(AppConstants.EMAIL_NOT_FOUND);
				}

			} else {
				user = new UserDTO();
				user.setReturnStatus(AppConstants.FAILURE);
				user.setReturnMessage(AppConstants.USER_NOT_FOUND);
			}
			return user;
		} catch (Exception ex) {
			throw new OSPBusinessException(AppConstants.VERIFICATION_MODULE,
					AppConstants.FUP_TOKEN__ERRCODE,
					AppConstants.FUP_TOKEN_ERRDESC, ex);

		} finally {
			logger.debug("Exiting LoginService << checkForUserAndSendLink() method");
		}

	}

  public String sendLinkForForgotPassword(UserBean userBean, HttpServletRequest request)
      throws RuntimeException {
    logger.debug("Entrying LoginService >> sendLinkForForgotPassword() method");
    try {
    String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());

    String linkTobeSend =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/verifyForgotPassword?username=" + encryptedUserName
            + "&UUID=" + userBean.getFupUUID();
    emailService.sendMail("FUP_VERIFY", userBean.getEmail(), linkTobeSend, "",
        "Forgot Password Link", userBean.getUserName());
    logger.debug("Exiting LoginService << sendLinkForForgotPassword() method");
    return AppConstants.SUCCESS;
    } catch (OSPBusinessException e) {
 	   logger.error(this.getClass(),e);
 	   return AppConstants.FAILURE;
 	}
  }

}
