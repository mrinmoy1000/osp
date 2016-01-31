package com.flamingos.osp.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.exception.OSPBusinessException;

public interface AdminService {

  public String approveProfile(OspProfessionalBean professional, HttpServletRequest request)
      throws OSPBusinessException;

  public OspProfessionalDTO professionalDetails(long profId) throws OSPBusinessException;

  public List<OspProfessionalDTO> allProfessionalDetails() throws OSPBusinessException;

  public List<OspProfessionalDTO> fetchApprovalProfList() throws OSPBusinessException;;
}
