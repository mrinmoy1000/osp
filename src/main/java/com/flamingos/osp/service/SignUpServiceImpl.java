package com.flamingos.osp.service;

import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.controller.ProfessionalController;
import com.flamingos.osp.dao.LoginDao;
import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.util.encoderDecoder;

@Service
public class SignUpServiceImpl implements SignUpService {

	@Autowired
	SignUpDao signUpDao;

	// private static final Logger logger =
	// Logger.getLogger(LoginServiceImpl.class);
	@Override
	public String createUser(UserBean userBean, HttpServletRequest request)throws OspServiceException {
		try {
			checkUniqueness(userBean);
			String returnMessage = createNewUser(userBean, "1", request);
			String userMessage = sendVerificationLink(userBean, request);
			return returnMessage;
		} catch (Exception e) {
			// TODO: handle exception
			throw new OspServiceException();
		}
	}

	public void checkUniqueness(UserBean loginBean) throws OspServiceException {

		// if(userDTO!=null &&
		// !loginBean.getPassword().equals(encryptionUtil.decryptPIData(userDTO.getUserPass())))
		try {
			UserDTO userDTOForUserName = signUpDao.findByUserName(loginBean
					.getUserName());
			if (userDTOForUserName != null) {
				throw new OspServiceException(
						"UserName already exists. Please use different one.");

			}
			UserDTO userDTOForContact = signUpDao.findByContact(loginBean
					.getContactNumber());
			if (userDTOForContact != null) {
				throw new OspServiceException(
						"Contact Number already exists. Please use forgot username/pass to retrieve account details if not able to login.");
			}
			UserDTO userDTOForEmail = signUpDao.findByEmailAddress(loginBean
					.getEmail());
			if (userDTOForEmail != null) {
				throw new OspServiceException(
						"Email id already exists. Please use forgot username/pass to retrieve account details if not able to login.");
			}

			// logger.debug("Creating user......");

		} catch (OspDaoException exp) {
			throw new OspServiceException();
		}

	}

	private String createNewUser(UserBean userBean, String role,
			HttpServletRequest request) throws OspServiceException {
		try {
			String Uuid = String.valueOf(UUID.randomUUID());
			userBean.setUUID(Uuid);
			String encryptedPassword = new encoderDecoder()
					.getEncodedValue(userBean.getPassword());
			userBean.setUserName(userBean.getUserName());
			userBean.setPassword(encryptedPassword);
			userBean.setActiveStatus("PEND");
			userBean.setEmailVerified("N");
			userBean.setSmsVerfied("N");
			userBean.setUserTypeCD(1);
			userBean.setFirstName(userBean.getFirstName());
			userBean.setMiddleName(userBean.getMiddleName());
			userBean.setLastName(userBean.getLastName());
			userBean.setRole_id(Integer.parseInt("role"));
			signUpDao.createNewUser(userBean);
			return "success";
		} catch (Exception e) {
			throw new OspServiceException();
		}

	}

	@Override
	public String sendVerificationLink(UserBean userBean,
			HttpServletRequest request) throws OspServiceException {
		// logger.debug("sending mail... ");
		String encryptedUserName = new encoderDecoder()
				.getEncodedValue(userBean.getUserName());
		String Uuid = userBean.getUUID();
		String linkTobeSend = request.getScheme() + "://"
				+ request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/verifyEmail?username="
				+ encryptedUserName + "&UUID=" + Uuid;
		return linkTobeSend;

	}


	@Override
	public void deleteUser(UserBean ub) {
		// TODO Auto-generated method stub

	}
}