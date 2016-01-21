package com.flamingos.osp.service.impl;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPConstants;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.EncoderDecoderUtil;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
//@Configuration
//@PropertySource("classpath:osp.properties")
public class ProfessionalServiceImpl implements ProfessionalService {

	@Autowired
	ProfessionalDao profDao;
	private static final Logger logger = Logger
			.getLogger(ProfessionalServiceImpl.class);
	//@Value("${osp.properties.emailExpireTimestamp}")
	private int emailExpireTime;

	//@Value("${osp.properties.smsExpireTimestamp}")
	private int smsExpireTime;

	@Autowired
	EncoderDecoderUtil encDecUtil;
	
	@Override
	public String verifyEmailDataAndUpdateStatus(String username,
			String  UUID, String type) throws OspServiceException {
		logger.debug("verfiying email....");
		try {

			String decryptedUserName = encDecUtil.getDecodedValue(username);
			UserBean user = new UserBean();
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
			UserDTO userDto = new UserDTO();
			access.setActiveIndicator(String.valueOf(0).charAt(0));
			if (type.equals("email")) {
				user.setEmailVerified("Y");
				user.setEmailUUID(UUID);
				access.setType(0);
				userDto = profDao.getUserLinkValidCheckForEmail(user, access);
				access.setActiveIndicator(String.valueOf(1).charAt(0));
				profDao.emailUpdateStatus(user, access);
			
			} else {
				if (type.equals("sms")) {
					user.setSmsVerfied("Y");
					user.setEmailUUID(UUID);
					access.setType(1);
					userDto = profDao.getUserLinkValidCheckForSms(user, access);
					access.setActiveIndicator(String.valueOf(1).charAt(0));
					profDao.smsUpdateStatus(user, access);			
				}
			}
			logger.info(OSPConstants.VALID);
			return OSPConstants.VALID;
		} catch (OspDaoException exp) {
			throw new OspServiceException(OSPConstants.INVALID_LINK);
		}

	}

	@Override
	public String verifyAndGenerateNewToken(String username,
			String UUID) throws OspServiceException {
		logger.debug("verfiying token....");
		try {
			UserBean user = new UserBean();
			String decryptedUserName = encDecUtil.getDecodedValue(username);
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
			List<UserBean> userList = null;
			userList = profDao.getTokenCheck(user, access);
			if (null !=userList&&userList.size()!=0) {
				
				for(UserBean userBean : userList)
				{
					if(userBean.getTokenType().equals("email") && user.getEmailUUID().equals(userBean.getEmailUUID()))
					{
						if(userBean.getEmailVerified().equals("N"))
						{
							user.setEmailVerified(userBean.getEmailVerified());
							profDao.generateNewEmailToken(user, emailExpireTime);	
						}
					}
					if(userBean.getTokenType().equals("sms") && user.getSmsUUID().equals(userBean.getSmsUUID()))
					{
						if(userBean.getSmsVerfied().equals("N"))
						{
							user.setSmsVerfied(userBean.getEmailVerified());
							profDao.generateNewSmsToken(user, smsExpireTime);	
						}
					}
						
				}
				
				logger.debug("token verfified and generated");
				return OSPConstants.SUCCESS;

			} else {
				logger.debug(OSPConstants.INVALID_LINK);
				return OSPConstants.INVALID_LINK;

			}
		} catch (OspDaoException exp) {
			throw new OspServiceException();
		}
	}
	}

