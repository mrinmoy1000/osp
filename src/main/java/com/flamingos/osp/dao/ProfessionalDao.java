package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfessionalDao {

  public void emailUpdateStatus(UserBean user, AccessToken access) throws OspDaoException;

  public void smsUpdateStatus(UserBean user, AccessToken access) throws OspDaoException;

  public void FUPUpdateStatus(UserBean user, AccessToken access) throws OspDaoException;

  public UserDTO getUserLinkValidCheckForEmail(UserBean user, AccessToken access)
      throws OspDaoException;

  public UserDTO getUserLinkValidCheckForSms(UserBean user, AccessToken access)
      throws OspDaoException;

  public UserDTO getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
      throws OspDaoException;
public int getTokenCheck(UserBean user, AccessToken access)
			throws OspDaoException;
	
	public UserDTO getTokenCheckforSms(UserBean user, AccessToken access)
			throws OspDaoException;

	public void generateNewToken(UserBean user, int expireTime)
			throws OspDaoException;


  public UserDTO checkForForgotPassword(UserBean user, AccessToken access) throws OspDaoException;

  public void updatePassword(UserBean user) throws OspDaoException;

  public int saveProfile(OspProfessionalBean professionalBean) throws OspDaoException;

  public void approveProfile(OspProfessionalBean professionalBean, int param_id)
      throws OspDaoException;

  public List<OspProfessionalDTO> getAllProfessionalDetails() throws OspDaoException;

  public OspProfessionalDTO getProfessionalDetails(int profId) throws OspDaoException;

  public List<OspProfSpecializationBean> getProfSpecializationList(int profId)
      throws OspDaoException;

  public List<OspProfAcademicsBean> getProfQualificationList(int profId) throws OspDaoException;

  public List<OspExperienceBean> getProfExperienceList(int profId) throws OspDaoException;
}
