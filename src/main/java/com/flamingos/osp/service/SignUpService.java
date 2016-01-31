package com.flamingos.osp.service;


import javax.servlet.http.HttpServletRequest;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;


public interface SignUpService {

  public UserDTO createUser(UserBean userBean, HttpServletRequest request)
      throws OSPBusinessException;

  public void deleteUser(UserBean ub);

  public void checkUniqueness(UserBean userBean) throws OspDaoException;

  public UserDTO checkUserName(String userName) throws OspDaoException;

  public String sendVerificationLinkinEmail(UserBean userBean, HttpServletRequest request)
      throws OspServiceException;

  public String sendVerificationLinkinSms(UserBean userBean, HttpServletRequest request)
      throws OspServiceException;

}
