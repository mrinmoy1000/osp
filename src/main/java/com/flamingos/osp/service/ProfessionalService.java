package com.flamingos.osp.service;

import javax.servlet.http.HttpServletRequest;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspServiceException;

public interface ProfessionalService {
  public UserDTO verifyEmailDataAndUpdateStatus(String username, String UUID, String type)
      throws OSPBusinessException;

  public String verifyAndGenerateNewToken(String username, String UUID) throws OSPBusinessException;

  public UserDTO verifyForgotPassword(String username, String UUID, String type)
      throws OSPBusinessException;

  public UserDTO changePassword(UserBean loginBean) throws OSPBusinessException;

  public String saveProfile(OspProfessionalBean professional, HttpServletRequest request)
      throws OspServiceException;

  public String approveProfile(OspProfessionalBean professional, HttpServletRequest request)
      throws OspServiceException;


}
