package com.flamingos.osp.service;

import javax.servlet.http.HttpServletRequest;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OSPBusinessException;

public interface AdminService {

  public String approveProfile(OspProfessionalBean professional, HttpServletRequest request)
      throws OSPBusinessException;


}
