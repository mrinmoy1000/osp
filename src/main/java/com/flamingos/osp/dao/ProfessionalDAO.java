package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.OspAddressBean;
import com.flamingos.osp.bean.OspContactBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;

public interface ProfessionalDAO {

	public void emailUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void smsUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void FUPUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public void updateTokenStatus(UserBean user, AccessToken access)
			throws OspDaoException;

	public UserDTO getUserLinkValidCheckForEmail(UserBean user,
			AccessToken access) throws OspDaoException;

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

	public UserDTO checkForForgotPassword(UserBean user, AccessToken access)
			throws OspDaoException;

	public void updatePassword(UserBean user) throws OspDaoException;

	public long addProfile(OspProfessionalBean professionalBean)
			throws OspDaoException;

	public long saveProfile(OspProfessionalBean professionalBean)
			throws OspDaoException;

	public void saveProfPublication(OspProfessionalBean professionalBean)
			throws OspDaoException;

	public void saveProfPresentation(OspProfessionalBean professionalBean)
			throws OspDaoException;

	public void approveProfile(OspProfessionalBean professionalBean,
			int param_id) throws OspDaoException;

	public List<OspProfessionalDTO> getAllProfessionalDetails()
			throws OspDaoException;

	public OspProfessionalDTO getProfessionalDetails(long profId)
			throws OspDaoException;

	public List<OspProfSpecializationBean> getProfSpecializationList(long profId)
			throws OspDaoException;

	public List<OspProfAcademicsBean> getProfQualificationList(long profId)
			throws OspDaoException;

	public List<OspExperienceBean> getProfExperienceList(long profId)
			throws OspDaoException;

	public List<OspProfessionalDTO> fetchApprovalProfList()
			throws OspDaoException;

	public List<OspContactBean> fetchContactDetails(long id);

	public List<OspAddressBean> fetchAddressDetails(long id);

	public void saveProfAcheivements(OspProfessionalBean professionalBean)
			throws Exception;

	public void saveProfRegMemNos(OspProfessionalBean professionalBean)
			throws Exception;

	public OspProfessionalDTO getProfessionaDetailsByRecordId(long recordId)
			throws OspDaoException;
}
