package com.flamingos.osp.service;

import javax.servlet.http.HttpServletRequest;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;


public interface LoginService {

  public UserDTO login(UserBean loginBean) throws OSPBusinessException;

  public UserDTO checkForUserAndSendLink(UserBean userBean, HttpServletRequest request)
      throws OSPBusinessException;

}
