package com.flamingos.osp.service;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspServiceException;

public interface ProfessionalService {
    public String verifyEmailDataAndUpdateStatus(String username, String UUID, String type)throws OspServiceException;
    
    public String verifyAndGenerateNewToken(String username, String UUID)throws OspServiceException;
}
