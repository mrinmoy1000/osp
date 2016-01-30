package com.flamingos.osp.service.impl;

import java.util.UUID;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.SignUpDao;
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
    SignUpDao signUpDao;

    @Autowired
    EncoderDecoderUtil encDecUtil;

    @Autowired
    EmailService emailService;

    @Autowired
    SmsService smsService;

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
                signUpDao.mapUserAndProfessional(userDto.getUserId(), prof.getUserId());
            }
            userBean.setEmail(userDto.getEmail());
            userBean.setContactNumber(Long.parseLong(userDto.getUserContact()));
            String userMessageForEmail = sendVerificationLinkinEmail(userBean, request);
            String userMessageForSMS = sendVerificationLinkinSms(userBean, request);
            logger.info("verfication email  link send = " + userMessageForEmail);
            logger.info("verfication sms link send = " + userMessageForSMS);
            userDto.setReturnStatus(AppConstants.SUCCESS);
            userDto.setReturnMessage("user created succcessfully");
            return userDto;
        } catch (Exception e) {
            throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE, AppConstants.SIGN_UP_EXCEPTION_ERRCODE, AppConstants.SIGN_UP_EXCEPTION_ERRDESC, e);
        } finally {
            logger.debug("Entrying SignUpService << createUser method");
        }
    }

    @Override
    public void checkUniqueness(UserBean loginBean) throws OspDaoException {
        logger.debug("Entrying SignUpService >> checkUniqueness method");
        UserDTO userDTOForUserName = signUpDao.findByUserName(loginBean.getUserName());
        if (userDTOForUserName != null) {
            throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE, AppConstants.DUPLICATE_USER_ERRCODE, AppConstants.DUPLICATE_USER_ERRDESC);
        }
        UserDTO userDTOForContact = signUpDao.findByContact(loginBean.getContactNumber());
        if (userDTOForContact != null) {
            throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE, AppConstants.DUPLICATE_CONTACT_ERRCODE, AppConstants.DUPLICATE_CONTACT_ERRDESC);
        }
        UserDTO userDTOForEmail = signUpDao.findByEmailAddress(loginBean.getEmail());
        if (userDTOForEmail != null) {
            throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE, AppConstants.DUPLICATE_EMAIL_ERRCODE, AppConstants.DUPLICATE_EMAIL_ERRDESC);
        }

        logger.debug("Exiting SignUpService << checkUniqueness method");
    }

    private void createNewUser(UserBean userBean) throws OspDaoException {
        logger.debug("Entrying SignUpService >> createNewUser method");
        userBean.setEmailUUID(String.valueOf(UUID.randomUUID()));
        userBean.setSmsUUID(String.valueOf(UUID.randomUUID()));
        String encryptedPassword = encDecUtil.getEncodedValue(userBean.getPassword());
        userBean.setPassword(encryptedPassword);
        userBean.setActiveStatus(0);
        userBean.setEmailVerified(0);
        userBean.setSmsVerfied(0);
        signUpDao.createNewUser(userBean, emailExpireTime, smsExpireTime);
        logger.debug("Exiting SignUpService << createNewUser method");

    }

    @Override
    public String sendVerificationLinkinEmail(UserBean userBean, HttpServletRequest request)
            throws OspServiceException {
        logger.debug("Entrying SignUpService >>  sendVerificationLinkinEmail() method");
        String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());
        String Uuid = userBean.getEmailUUID();
        String linkTobeSend
                = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/verifyEmail?username=" + encryptedUserName + "&UUID="
                + Uuid;
        String generatelinkTobeSend = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/generateNewToken?username=" + encryptedUserName + "&UUID=" + Uuid;
        emailService.sendMail("EMAIL_VERIFY", userBean.getEmail(), linkTobeSend, generatelinkTobeSend, emailVerficationMessage, userBean.getUserName());
        logger.debug("Exiting SignUpService <<  sendVerificationLinkinEmail() method");
        return linkTobeSend;

    }

    @Override
    public String sendVerificationLinkinSms(UserBean userBean, HttpServletRequest request)
            throws OspServiceException {
        logger.debug("Entrying SignUpService >>  sendVerificationLinkinSms() method");
        String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());
        String Uuid = userBean.getSmsUUID();
        String linkTobeSend = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/verifySms?username=" + encryptedUserName + "&UUID=" + Uuid;
        smsService.sendSms(String.valueOf(userBean.getContactNumber()), "SMS_VERIFY", linkTobeSend);
        logger.debug("Exiting SignUpService <<  sendVerificationLinkinSms() method");
        return linkTobeSend;

    }

    @Override
    public void deleteUser(UserBean ub) {
        // TODO Auto-generated method stub

    }

    @Override
    public UserDTO checkUserName(UserBean userBean) throws OspDaoException {
          logger.debug("Entrying SignUpService >>  checkUserName() method");
        try {
            UserDTO userDTOForUserName = signUpDao.findByUserName(userBean
                    .getUserName());
            if (userDTOForUserName != null) {
                throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
                        AppConstants.DUPLICATE_USER_ERRCODE,
                        AppConstants.DUPLICATE_USER_ERRDESC);
            }
            userDTOForUserName = new UserDTO();
            userDTOForUserName.setReturnStatus(AppConstants.SUCCESS);
            return userDTOForUserName;
        } catch (OspDaoException exp) {
            throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE,
                    AppConstants.SIGN_UP_EXCEPTION_ERRCODE,
                    AppConstants.SIGN_UP_EXCEPTION_ERRDESC);
        }
        finally
        {logger.debug("Exiting SignUpService <<  checkUserName() method");
        }

    }
}
