package com.flamingos.osp.service;

import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.controller.ProfessionalController;
import com.flamingos.osp.dao.LoginDao;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.util.EncoderDecoderUtil;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public interface LoginService {

	public UserDTO login(UserBean loginBean) throws OspServiceException;

	public String checkForUserAndSendLink(UserBean userBean,
			HttpServletRequest request) throws OspServiceException;
	
	public String sendLinkForForgotPassword(UserBean userBean,
			HttpServletRequest request) throws RuntimeException;
	


}
