package com.flamingos.osp.dao;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfessionalDao {

	public void emailUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void smsUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void FUPUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public int getUserLinkValidCheckForEmail(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public int getUserLinkValidCheckForSms(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public int getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
			throws OspDaoException;

}
