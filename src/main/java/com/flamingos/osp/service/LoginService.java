package com.flamingos.osp.service;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OSPBusinessException;

import javax.servlet.http.HttpServletRequest;


public interface LoginService {

	public UserDTO login(UserBean loginBean) throws OSPBusinessException;

	public UserDTO checkForUserAndSendLink(UserBean userBean,
			HttpServletRequest request) throws OSPBusinessException;

}
