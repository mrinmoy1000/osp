package com.flamingos.osp.service;


import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspServiceException;

import javax.servlet.http.HttpServletRequest;


public interface SignUpService {

	public String createUser(UserBean userBean,HttpServletRequest request)
			throws OspServiceException;

	public void deleteUser(UserBean ub);

	public void checkUniqueness(UserBean userBean) throws OspServiceException;

	public String sendVerificationLinkinEmail(UserBean userBean,HttpServletRequest request) throws OspServiceException;
	
	public String sendVerificationLinkinSms(UserBean userBean,HttpServletRequest request) throws OspServiceException;

}
