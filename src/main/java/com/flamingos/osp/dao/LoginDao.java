package com.flamingos.osp.dao;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspDaoException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.flamingos.osp.dto.UserDTO;

public interface LoginDao {

	public UserDTO getUser(UserBean user)throws OspDaoException ;

    public int checkForUser(UserBean usrBean)throws OspDaoException ;
    public UserBean login(UserBean usrBean) ;

    public String addFUPAccessToken(UserBean user)throws OspDaoException ;   
}
