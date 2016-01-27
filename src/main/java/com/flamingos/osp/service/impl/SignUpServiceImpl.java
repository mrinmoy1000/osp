package com.flamingos.osp.service.impl;

import java.util.UUID;
import org.apache.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
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

@Service
public class SignUpServiceImpl implements SignUpService {

    @Value("${email.expire.time}")
    private int emailExpireTime;

    @Value("${sms.expire.time}")
    private int smsExpireTime;

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
            logger.debug("create User");
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
            logger.info("verfication email  link send" + userMessageForEmail);
            logger.info("verfication sms link send" + userMessageForSMS);
            userDto.setReturnStatus("success");
            userDto.setReturnMessage("user created succcessfully");
            return userDto;
        } catch (Exception e) {
            throw new OSPBusinessException(AppConstants.SIGN_UP_MODULE, AppConstants.SIGN_UP_EXCEPTION_ERRCODE, AppConstants.SIGN_UP_EXCEPTION_ERRDESC, e);
        }
    }

    @Override
    public void checkUniqueness(UserBean loginBean) throws OSPBusinessException {
        UserDTO userDTOForUserName = signUpDao.findByUserName(loginBean.getUserName());
        if (userDTOForUserName != null) {
            // throw new OspDaoException("UserName already exists. Please use different one.");

        }
        UserDTO userDTOForContact = signUpDao.findByContact(loginBean.getContactNumber());
        if (userDTOForContact != null) {
            // throw new OspDaoException(    "Contact Number already exists. Please use forgot username/pass to retrieve account details if not able to login.");
        }
        UserDTO userDTOForEmail = signUpDao.findByEmailAddress(loginBean.getEmail());
        if (userDTOForEmail != null) {
            //    throw new OspDaoException(    "Email id already exists. Please use forgot username/pass to retrieve account details if not able to login.");
        }

        // logger.debug("Creating user......");
    }

    private void createNewUser(UserBean userBean) throws OSPBusinessException {

        userBean.setEmailUUID(String.valueOf(UUID.randomUUID()));
        userBean.setSmsUUID(String.valueOf(UUID.randomUUID()));
        String encryptedPassword = encDecUtil.getEncodedValue(userBean.getPassword());
        userBean.setUserName(userBean.getUserName());
        userBean.setPassword(encryptedPassword);
        userBean.setActiveStatus(0);
        userBean.setEmailVerified(0);
        userBean.setSmsVerfied(0);
        userBean.setUserTypeCD(1); // TODO .. What is this variable.
        userBean.setFirstName(userBean.getFirstName());
        userBean.setMiddleName(userBean.getMiddleName());
        userBean.setLastName(userBean.getLastName());
        userBean.setRoleId(userBean.getRoleId());
        signUpDao.createNewUser(userBean, emailExpireTime, smsExpireTime);

    }

    @Override
    public String sendVerificationLinkinEmail(UserBean userBean, HttpServletRequest request)
            throws OspServiceException {
        logger.debug("sending mail... ");
        String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());
        String Uuid = userBean.getEmailUUID();
        String linkTobeSend
                = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() + "/verifyEmail?username=" + encryptedUserName + "&UUID="
                + Uuid;
   emailService.sendMail("EMAIL_VERIFY", userBean.getEmail(),linkTobeSend,"Registration Email",userBean.getUserName());
        logger.debug("Email sent... ");
        return linkTobeSend;

    }

    @Override
    public String sendVerificationLinkinSms(UserBean userBean, HttpServletRequest request)
            throws OspServiceException {
        logger.debug("sending SMS... ");
        String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());
        String Uuid = userBean.getSmsUUID();
        String linkTobeSend = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath() + "/verifySms?username=" + encryptedUserName + "&UUID="+ Uuid;
        smsService.sendSms(String.valueOf(userBean.getContactNumber()),"EMAIL_VERIFY", linkTobeSend);
        logger.debug("SMS SENT  ... ");
        return linkTobeSend;

    }

    @Override
    public void deleteUser(UserBean ub) {
        // TODO Auto-generated method stub

    }

    @Override
    public String checkUserName(UserBean userBean) throws OSPBusinessException {
        try {
            UserDTO userDTOForUserName = signUpDao.findByUserName(userBean.getUserName());
            if (userDTOForUserName != null) {
                //   throw new OspServiceException("UserName already exists. Please use different one.");
            }

        } catch (OSPBusinessException exp) {
            logger.error(exp);
            //  throw new OspServiceException(exp);
        }
        return "success";
    }
}
