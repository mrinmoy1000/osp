package com.flamingos.osp.service;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPConstant;
import com.flamingos.osp.controller.ProfessionalController;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.util.encoderDecoder;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;

public class ProfessionalService {

    @Autowired
    ProfessionalDao profDao;
    private static final Logger logger = Logger.getLogger(ProfessionalController.class);

    public String checkUniqueness(UserBean userBean, HttpServletRequest request)throws OspServiceException {
        logger.debug("cheking uniqueness service method");         
        	try {
        		profDao.checkUniqueness(userBean);
        		createUser(userBean, request);
                 return OSPConstant.SUCCESS;
			} catch (OspDaoException e) {
				logger.debug("Exception in service method for check uniqueness");
				throw new OspServiceException(e,e.getMessage());
				// TODO: handle exception       
    }
    }

    public void createUser(UserBean userBean, HttpServletRequest request){
        logger.debug("Creating user......");
        String userMessage="";
        String Uuid = String.valueOf(UUID.randomUUID());
        userBean.setUUID(Uuid);
        String encryptedPassword = new encoderDecoder().getEncodedValue(userBean.getPassword());
        userBean.setPassword(encryptedPassword);
        userBean.setActiveStatus("PEND");
        userBean.setEmailVerified("N");
        userBean.setSmsVerfied("N");
        userBean.setUserTypeCD(1);
        try {
        	 profDao.createNewUser(userBean);
        	 userMessage = sendVerificationLink(userBean, request);
        	 logger.debug("user created and "+userMessage +"mail sent");
		} catch (RuntimeException  e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
       
       
         
    }

    public String sendVerificationLink(UserBean userBean, HttpServletRequest request)throws RuntimeException {
       logger.debug("sending mail... ");
        String encryptedUserName = new encoderDecoder().getEncodedValue(userBean.getUserName());
        String Uuid = userBean.getUUID();
        String linkTobeSend = request.getScheme() + "://"
                + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath()
                + "/verifyEmail?username=" + encryptedUserName + "&UUID=" + Uuid;
        return linkTobeSend;
    }

    public String verifyEmailDataAndUpdateStatus(String username, String UUID, String type)throws OspServiceException {
        logger.debug("verfiying email....");
        String decryptedUserName = new encoderDecoder().getDecodedValue(username);
        UserBean userBean = new UserBean();
        userBean.setUserName(decryptedUserName);
        userBean.setUUID(UUID);
        AccessToken access = new AccessToken();
        access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
        access.setActiveIndicator('Y');
        access.setType(type);
        int count = profDao.getUserLinkValidCheck(userBean, access);
        if (count > 0) {
            if (access.getType().equals("email")) {
                userBean.setEmailVerified("Y");
            } else {
                if (access.getType().equals("sms")) {
                    userBean.setSmsVerfied("Y");
                }
            }

            access.setActiveIndicator('N');
            try {
            	 profDao.emailUpdateStatus(userBean, access);
                 logger.debug("link verfified");
                 return "";
			} catch (OspDaoException e) {
				// TODO: handle exception
				 logger.debug(e);
				 return e.getMessage();
			}           
            
        } else {
            logger.debug(OSPConstant.INVALID_LINK);
            return OSPConstant.INVALID_LINK;
            
        }

    }

}
