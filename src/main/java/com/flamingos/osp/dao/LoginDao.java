package com.flamingos.osp.dao;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;

public interface LoginDao {

    public UserDTO getUser(UserBean user) throws OSPBusinessException;

    public UserDTO checkForUser(UserBean usrBean) throws OSPBusinessException;

    public int addFUPAccessToken(UserBean user, int fupExpireTime) throws OSPBusinessException;

}
