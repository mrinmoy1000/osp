package com.flamingos.osp.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.LoginDao;
import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.LoginService;
import com.flamingos.osp.util.EncoderDecoderUtil;
import java.util.UUID;

@Service
@Configuration
@PropertySource("classpath:osp.properties")
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;
@Autowired
	SignUpDao signUpDao;
	@Autowired
	EncoderDecoderUtil encDecUtil;
	
	@Value("${fup.expire.time}")
	private String fupExpireTime;
	 private static final Logger logger =
	 Logger.getLogger(LoginServiceImpl.class);
@Override
	public UserDTO login(UserBean loginBean) throws OspServiceException {
		String encryptedPassword =encDecUtil.getEncodedValue(loginBean.getPassword());
		loginBean.setPassword(encryptedPassword);
		try {
			UserDTO userDTO = loginDao.getUser(loginBean);
			if (userDTO != null) {
				if (!userDTO.getUserPass().equals(encryptedPassword)) {
					 logger.debug("login authentication ended");
					userDTO = null;
				}
			}

			return userDTO;
		} catch (OspDaoException exp) {
			throw new OspServiceException();
		}

	}

	public String checkForUserAndSendLink(UserBean userBean,
			HttpServletRequest request) throws OspServiceException {
		 logger.debug("user checking for link");
		try {
			UserDTO user = loginDao.checkForUser(userBean);
			String userMessage;
			if (user != null) {
				String Uuid =  String.valueOf(UUID.randomUUID());
			userBean.setFupUUID(Uuid);
			userBean.setUser_id(user.getUserId());
				loginDao.addFUPAccessToken(userBean,Integer.parseInt(fupExpireTime));
				userMessage = sendLinkForForgotPassword(userBean, request);
			} else {
				// logger.debug("user checking done , User not found");
				return "User doesn't exist";
			}
			// logger.debug("user checking done, sending mail");
			return userMessage;
		} catch (OspDaoException ex) {
			throw new OspServiceException(ex);

		}

	}

	public String sendLinkForForgotPassword(UserBean userBean,
			HttpServletRequest request) throws RuntimeException {
		String encryptedUserName = encDecUtil.getEncodedValue(userBean.getUserName());

		String linkTobeSend = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/verifyForgotPassword?username="
				+ encryptedUserName + "&UUID=" + userBean.getFupUUID();
		return linkTobeSend;
	}

	

	

}
