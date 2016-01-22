package com.flamingos.osp.service;

import javax.servlet.http.HttpServletRequest;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspServiceException;

public interface ProfessionalService {
    public UserDTO verifyEmailDataAndUpdateStatus(String username, String UUID, String type)throws OspServiceException;
    
    public String verifyAndGenerateNewToken(String username, String UUID)throws OspServiceException;
    
    public UserDTO verifyForgotPassword(String username, String UUID, String type)throws OspServiceException;
    
	public UserDTO changePassword(UserBean loginBean) throws OspServiceException;
	
	public String addProfile(OspProfessionalBean professional, HttpServletRequest request)throws OspServiceException;
	public String approveProfile(OspProfessionalBean professional, HttpServletRequest request)throws OspServiceException;
}
