package com.flamingos.osp.service;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspServiceException;

public interface ProfessionalService {
    public String verifyEmailDataAndUpdateStatus(String username, UserBean user, String type)throws OspServiceException;
}
