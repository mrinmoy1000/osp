package com.flamingos.osp.dao;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;

public interface LoginDAO {

  public UserDTO getUser(UserBean user) throws OspDaoException;

  public UserDTO checkForUser(UserBean usrBean) throws OspDaoException;

  public int addFUPAccessToken(UserBean user, int fupExpireTime) throws OspDaoException;

}
