package com.flamingos.osp.service.impl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.LoginDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.LoginService;
import com.flamingos.osp.util.encoderDecoder;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginDao loginDao;

	// private static final Logger logger =
	// Logger.getLogger(LoginServiceImpl.class);

	public UserDTO createUser(UserBean loginBean) throws OspServiceException {

		// if(userDTO!=null &&
		// !loginBean.getPassword().equals(encryptionUtil.decryptPIData(userDTO.getUserPass())))
		String encryptedPassword = new encoderDecoder()
				.getEncodedValue(loginBean.getPassword());
		loginBean.setPassword(encryptedPassword);
		try {
			UserDTO userDTO = loginDao.getUser(loginBean);
			if (userDTO != null) {
				if (!userDTO.getUserPass().equals(encryptedPassword)) {
					// logger.debug("login authentication ended");
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
		// logger.debug("user checking for link");
		try {
			int check = loginDao.checkForUser(userBean);
			String userMessage;
			if (check > 0) {
			//	String Uuid = userBean.getUUID();
			//	userBean.setUUID(Uuid);
				loginDao.addFUPAccessToken(userBean);

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
		String encryptedUserName = new encoderDecoder()
				.getEncodedValue(userBean.getUserName());

		String linkTobeSend = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/verifyForgotPassword?username="
				+ encryptedUserName + "&UUID=" + userBean.getFupUUID();
		return linkTobeSend;
	}

	@Override
	public UserDTO createUser(UserBean loginBean, HttpServletRequest request)
			throws OspServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
