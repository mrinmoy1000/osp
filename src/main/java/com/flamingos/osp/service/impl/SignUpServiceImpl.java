package com.flamingos.osp.service.impl;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.SignUpDAO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.EmailService;
import com.flamingos.osp.service.SignUpService;
import com.flamingos.osp.service.SmsService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.EncoderDecoderUtil;

@Transactional
@Service
public class SignUpServiceImpl implements SignUpService {

  @Value("${email.expire.time}")
  private int emailExpireTime;

  @Value("${sms.expire.time}")
  private int smsExpireTime;

  @Value("${email.verification.message}")
  private String emailVerficationMessage;

  @Value("${sms.verification.message}")
  private String smsVerificationMessage;
  @Autowired
  SignUpDAO signUpDao;

  @Autowired
  EncoderDecoderUtil encDecUtil;

  @Autowired
  EmailService emailService;

  @Autowired
  SmsService smsService;
  @Autowired
  private ConfigParamBean configParamBean;

  private static final Logger logger = Logger.getLogger(SignUpServiceImpl.class);

	@Override
	public UserDTO createUser(UserBean userBean, HttpServletRequest request)
			throws OSPBusinessException {
		try {
			logger.debug("Entrying SignUpService >> createUser method");
			checkUniqueness(userBean);
			createNewUser(userBean);
			UserDTO userDto = signUpDao.findByUserName(userBean.getUserName());
			if (userBean.getProf_id() != null) {
				UserDTO prof = signUpDao.checkForProfessional(userBean);
				if (prof != null) {
					signUpDao.mapUserAndProfessional(userDto.getUserId(),
							userBean.getProf_id());
				}
			}
			userDto.setReturnStatus(AppConstants.SUCCESS);
		
			userBean.setEmail(userDto.getEmail());
			userBean.setContactNumber(Long.parseLong(userDto.getUserContact()));
			String userMessageForEmail = sendVerificationLinkinEmail(userBean,request);
			String userMessageForSMS = sendVerificationLinkinSms(userBean,request);
			if (userMessageForEmail.equals(AppConstants.SUCCESS) && userMessageForSMS.equals(AppConstants.SUCCESS)) {
				userDto.setReturnMessage(AppConstants.USER_CREATED_SUCCESS+" "+AppConstants.VERIFICATION_LINK_SMS_NOTIFICATION);				
			} else {
				if (userMessageForEmail.equals(AppConstants.SUCCESS)) {
					userDto.setReturnMessage(AppConstants.USER_CREATED_SUCCESS+" "+AppConstants.VERIFICATION_LINK_NOTIFICATION);	
				}
				if (userMessageForSMS.equals(AppConstants.SUCCESS)) {
					userDto.setReturnMessage(AppConstants.USER_CREATED_SUCCESS+" "+AppConstants.VERIFICATION_SMS_NOTIFICATION);	
				}
				if (userMessageForEmail.equals(AppConstants.FAILURE) && userMessageForSMS.equals(AppConstants.FAILURE)) {
					userDto.setReturnMessage(AppConstants.USER_CREATED_SUCCESS);	
				}
			}
						
			return userDto;
		} catch (OSPBusinessException e) {
			throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
					AppConstants.SIGN_UP_EXCEPTION_ERRCODE,
					e.getErrorDescription(), e);
		} catch (Exception e) {
			throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
					AppConstants.SIGN_UP_EXCEPTION_ERRCODE,
					AppConstants.SIGN_UP_EXCEPTION_ERRDESC, e);
		} finally {
			logger.debug("Entrying SignUpService << createUser method");
		}
	}

  @Override
  public void checkUniqueness(UserBean loginBean) throws OspDaoException {
    logger.debug("Entrying SignUpService >> checkUniqueness method");
    UserDTO userDTOForUserName = signUpDao.findByUserName(loginBean.getUserName());
    if (userDTOForUserName != null) {
      throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
          AppConstants.DUPLICATE_USER_ERRCODE, AppConstants.DUPLICATE_USER_ERRDESC);
    }
    UserDTO userDTOForContact = signUpDao.findByContact(loginBean.getContactNumber());
    if (userDTOForContact != null) {
      throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
          AppConstants.DUPLICATE_CONTACT_ERRCODE, AppConstants.DUPLICATE_CONTACT_ERRDESC);
    }
    UserDTO userDTOForEmail = signUpDao.findByEmailAddress(loginBean.getEmail());
    if (userDTOForEmail != null) {
      throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
          AppConstants.DUPLICATE_EMAIL_ERRCODE, AppConstants.DUPLICATE_EMAIL_ERRDESC);
    }

    logger.debug("Exiting SignUpService << checkUniqueness method");
  }

  private void createNewUser(UserBean userBean) throws OspDaoException {
    logger.debug("Entrying SignUpService >> createNewUser method");
    userBean.setEmailUUID(String.valueOf(UUID.randomUUID()));
    userBean.setSmsUUID(String.valueOf(UUID.randomUUID()));
    String encryptedPassword = encDecUtil.getEncodedValue(userBean.getPassword());
    userBean.setPassword(encryptedPassword);
    ConfigParamDTO oParamUserStatus =
            configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_STATUS,
                AppConstants.PARAM_NAME_USER_STATUS_INACTIVE);
    userBean.setActiveStatus(oParamUserStatus.getParameterid());
    ConfigParamDTO oParamEmailStatus =
            configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_CONTACT_VERIFIED,
                AppConstants.PARAM_NAME_EMAIL_NOT_VERIFIED);
    userBean.setEmailVerified(oParamEmailStatus.getParameterid());
    ConfigParamDTO oParamSMSStatus =
            configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_CONTACT_VERIFIED,
                AppConstants.PARAM_NAME_PHONE_NOT_VERIFIED);
    userBean.setSmsVerfied(oParamSMSStatus.getParameterid());
    ConfigParamDTO oParamTokenUsed =
            configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_TOKEN_STATUS,
                AppConstants.PARAM_NAME_NOT_YET_USED);
    userBean.setTokenIsUsed(oParamTokenUsed.getParameterid());
    signUpDao.createNewUser(userBean, emailExpireTime, smsExpireTime);
    logger.debug("Exiting SignUpService << createNewUser method");

  }

  @Override
  public String sendVerificationLinkinEmail(UserBean userBean, HttpServletRequest request)
      throws OspServiceException {
    logger.debug("Entrying SignUpService >>  sendVerificationLinkinEmail() method");
   try {
	
 String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());
    String Uuid = userBean.getEmailUUID();
    String linkTobeSend =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/verifyEmail?username=" + encryptedUserName + "&UUID="
            + Uuid;
    String generatelinkTobeSend =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/generateNewToken?username=" + encryptedUserName;
    emailService.sendMail("EMAIL_VERIFY", userBean.getEmail(), linkTobeSend, generatelinkTobeSend,
        emailVerficationMessage, userBean.getUserName());
    logger.info("verfication email  link send = " + linkTobeSend);
    logger.debug("Exiting SignUpService <<  sendVerificationLinkinEmail() method");    
    return AppConstants.SUCCESS;
   } catch (OSPBusinessException e) {
	   logger.error(this.getClass(),e);
	   return AppConstants.FAILURE;
	}

  }

  @Override
  public String sendVerificationLinkinSms(UserBean userBean, HttpServletRequest request)
      throws OspServiceException {
    logger.debug("Entrying SignUpService >>  sendVerificationLinkinSms() method");
    try {
    String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());
    String Uuid = userBean.getSmsUUID();
  //  String linkTobeSend =request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath() + "/verifySms?username=" + encryptedUserName + "&UUID="+ Uuid;
      String linkTobeSend = "http://192.168.0.21:8080/osp/verifySms?username=" + encryptedUserName + "&UUID="
            + Uuid;
    smsService.sendSms(String.valueOf(userBean.getContactNumber()), "SMS_VERIFY", linkTobeSend);
    logger.info("verfication sms link send = " + linkTobeSend);
    logger.debug("Exiting SignUpService <<  sendVerificationLinkinSms() method");
    return AppConstants.SUCCESS;
    } catch (OSPBusinessException e) {
 	   logger.error(this.getClass(),e);
 	   return AppConstants.FAILURE;
 	}


  }

  @Override
  public void deleteUser(UserBean ub) {
    // TODO Auto-generated method stub

  }

  @Override
  public UserDTO checkUserName(String userName) throws OspDaoException {
    logger.debug("Entrying SignUpService >>  checkUserName() method");
    try {
      UserDTO userDTOForUserName = signUpDao.findByUserName(userName);
      if (userDTOForUserName != null) {
        throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
            AppConstants.DUPLICATE_USER_ERRCODE, AppConstants.DUPLICATE_USER_ERRDESC);
      }
      userDTOForUserName = new UserDTO();
      userDTOForUserName.setReturnStatus(AppConstants.SUCCESS);
      return userDTOForUserName;
    } catch (OspDaoException exp) {
      throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
          AppConstants.SIGN_UP_EXCEPTION_ERRCODE, AppConstants.SIGN_UP_EXCEPTION_ERRDESC);
    } finally {
      logger.debug("Exiting SignUpService <<  checkUserName() method");
    }

  }
}
