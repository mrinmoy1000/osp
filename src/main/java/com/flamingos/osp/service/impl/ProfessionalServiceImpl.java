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
import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPConstants;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.AppConstants;
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
	
	@Autowired
	private ConfigParamBean configParamBean;

	ConfigParamDto userStatusBean = null;

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

			//access.setActiveIndicator(String.valueOf(0).charAt(0));
			if (type.equals(OSPConstants.EMAIL_TYPE)) {
				user.setEmailVerified(1);
				user.setEmailUUID(UUID);
				access.setType(0);
				userDto= profDao.getUserLinkValidCheckForEmail(user,
						access);
				user.setActiveStatus(1);
				if(userDto!=null)
				profDao.emailUpdateStatus(user, access);

			} else {
				if (type.equals(OSPConstants.SMS_TYPE)) {
					user.setSmsVerfied(1);
					user.setSmsUUID(UUID);
					access.setType(1);
					 userDto = profDao.getUserLinkValidCheckForSms(user,
							access);
					 user.setActiveStatus(1);
					 if(userDto!=null)
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
						if (userBean.getEmailVerified()==0) {
							user.setEmailVerified(userBean.getEmailVerified());
							profDao.generateNewEmailToken(user,
									Integer.parseInt(emailExpireTime));
						}
					}
					if (userBean.getTokenType().equals(OSPConstants.SMS_TYPE)
							&& user.getSmsUUID().equals(userBean.getSmsUUID())) {
						if (userBean.getSmsVerfied()==0) {
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
			userBean.setPassword(encDecUtil.getEncodedValue(userBean.getPassword()));
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
		userStatusBean = configParamBean.getParameterByCodeName(
				AppConstants.PARAM_CODE_USER_STATUS,
				AppConstants.PARAM_NAME_INITIAL);
		try {
			profDao.approveProfile(professional,
					userStatusBean.getParameterid());
		} catch (OspDaoException ex) {
			throw new OspServiceException(ex);

		}

		return null;
	}
}
