package com.flamingos.osp.dao;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;

public interface SignUpDao {

	public UserDTO findByUserName(String userName) throws OspDaoException;

	public UserDTO findByEmailAddress(String emailAddress)throws OspDaoException;

	public UserDTO findByContact(Long contact) throws OspDaoException;

	public void createNewUser(UserBean user,int emailExpireTime,int smsExpireTime) throws OspDaoException;

	public String updateUser(UserBean user) throws OspDaoException;
	
	public UserDTO checkForProfessional(UserBean user) throws OspDaoException;
	
	public void mapUserAndProfessional(long userId,long professionalId) throws OspDaoException;
}
