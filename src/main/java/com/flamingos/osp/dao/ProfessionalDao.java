package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfessionalDao {

	public void emailUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void smsUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void FUPUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public UserDTO getUserLinkValidCheckForEmail(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public UserDTO getUserLinkValidCheckForSms(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public UserDTO getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public List<UserBean> getTokenCheck(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public int generateNewEmailToken(UserBean user,int expireTime) throws OspDaoException;
	
	public int generateNewSmsToken(UserBean user,int expireTime) throws OspDaoException;


}
