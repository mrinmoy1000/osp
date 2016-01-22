package com.flamingos.osp.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPConstants;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.EncoderDecoderUtil;

@Service
@Configuration
@PropertySource("classpath:osp.properties")
public class ProfessionalServiceImpl implements ProfessionalService {

	@Autowired
	ProfessionalDao profDao;

	private static final Logger logger = Logger
			.getLogger(ProfessionalServiceImpl.class);

	@Value("${email.expire.time}")
	private String emailExpireTime;

	@Value("${sms.expire.time}")
	private String smsExpireTime;

	@Autowired
	EncoderDecoderUtil encDecUtil;

	@Override
	public UserDTO verifyEmailDataAndUpdateStatus(String username, String UUID,
			String type) throws OspServiceException {
		logger.debug("verfiying email....");
		UserDTO userDto = new UserDTO();
		try {

			String decryptedUserName = encDecUtil.getDecodedValue(username);
			UserBean user = new UserBean();
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));

			access.setActiveIndicator(String.valueOf(0).charAt(0));
			if (type.equals(OSPConstants.EMAIL_TYPE)) {
				user.setEmailVerified(OSPConstants.LINK_VERFIED);
				user.setEmailUUID(UUID);
				access.setType(0);
				userDto= profDao.getUserLinkValidCheckForEmail(user,
						access);
				access.setActiveIndicator(String.valueOf(1).charAt(0));
				profDao.emailUpdateStatus(user, access);

			} else {
				if (type.equals(OSPConstants.SMS_TYPE)) {
					user.setSmsVerfied(OSPConstants.LINK_VERFIED);
					user.setEmailUUID(UUID);
					access.setType(1);
					 userDto = profDao.getUserLinkValidCheckForSms(user,
							access);
					access.setActiveIndicator(String.valueOf(1).charAt(0));
					profDao.smsUpdateStatus(user, access);
				}
			}
			logger.info(OSPConstants.VALID);
			return userDto;
		} catch (OspDaoException exp) {
			throw new OspServiceException(OSPConstants.INVALID_LINK);
		}

	}

	@Override
	public String verifyAndGenerateNewToken(String username, String UUID)
			throws OspServiceException {
		logger.debug("verfiying token....");
		try {
			UserBean user = new UserBean();
			String decryptedUserName = encDecUtil.getDecodedValue(username);
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
			List<UserBean> userList = null;
			userList = profDao.getTokenCheck(user, access);
			if (null != userList && userList.size() != 0) {

				for (UserBean userBean : userList) {
					if (userBean.getTokenType().equals(OSPConstants.EMAIL_TYPE)
							&& user.getEmailUUID().equals(
									userBean.getEmailUUID())) {
						if (userBean.getEmailVerified().equals(
								OSPConstants.LINK_NOT_VERFIED)) {
							user.setEmailVerified(userBean.getEmailVerified());
							profDao.generateNewEmailToken(user,
									Integer.parseInt(emailExpireTime));
						}
					}
					if (userBean.getTokenType().equals(OSPConstants.SMS_TYPE)
							&& user.getSmsUUID().equals(userBean.getSmsUUID())) {
						if (userBean.getSmsVerfied().equals(
								OSPConstants.LINK_NOT_VERFIED)) {
							user.setSmsVerfied(userBean.getEmailVerified());
							profDao.generateNewSmsToken(user,
									Integer.parseInt(smsExpireTime));
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

	@Override
	public UserDTO verifyForgotPassword(String username, String UUID,
			String type) throws OspServiceException {
		logger.debug("verfiying forgot password method....");
		UserDTO userDto = new UserDTO();
		try {

			String decryptedUserName = encDecUtil.getDecodedValue(username);
			UserBean user = new UserBean();
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
				user.setFupUUID(UUID);
				access.setType(0);
				userDto= profDao.checkForForgotPassword(user, access);
			logger.info(OSPConstants.VALID);
			return userDto;
		} catch (OspDaoException exp) {
			throw new OspServiceException(OSPConstants.INVALID_LINK);
		}
	}

	@Override
	public UserDTO changePassword(UserBean userBean) throws OspServiceException {
		try {
			profDao.updatePassword(userBean);
			UserDTO user = new UserDTO();
			user.setActivationStatus("succcess");
			user.setReturnMessage("password changed successfully");
			return user;
		} catch (OspDaoException ex) {
			throw new OspServiceException(ex);

		}
	}
	
	@Override
	public String addProfile(OspProfessionalBean professional,
			HttpServletRequest request) throws OspServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String approveProfile(OspProfessionalBean professional,
			HttpServletRequest request) throws OspServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
