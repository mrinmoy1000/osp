package com.flamingos.osp.service;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspServiceException;

public interface ProfessionalService {
    public UserDTO verifyEmailDataAndUpdateStatus(String username, String UUID, String type)throws OspServiceException;
    
    public String verifyAndGenerateNewToken(String username, String UUID)throws OspServiceException;
    
    public UserDTO verifyForgotPassword(String username, String UUID, String type)throws OspServiceException;
    
	public UserDTO changePassword(UserBean loginBean) throws OspServiceException;
}
