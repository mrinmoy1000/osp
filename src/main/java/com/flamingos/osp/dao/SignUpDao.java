package com.flamingos.osp.dao;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspDaoException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.flamingos.osp.dto.UserDTO;

@Transactional(propagation = Propagation.REQUIRED)
public interface SignUpDao {

	public UserDTO findByUserName(String userName) throws OspDaoException;

	public UserDTO findByEmailAddress(String emailAddress)throws OspDaoException;

	public UserDTO findByContact(Long contact) throws OspDaoException;

	public int createNewUser(UserBean user) throws OspDaoException;

	public String updateUser(UserBean user) throws OspDaoException;
}
