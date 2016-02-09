package com.flamingos.osp.service.impl;

import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.AddressDAO;
import com.flamingos.osp.dao.ContactDAO;
import com.flamingos.osp.dao.ExperienceBeanDAO;
import com.flamingos.osp.dao.ProfAcademicsBeanDAO;
import com.flamingos.osp.dao.ProfAddressMapDAO;
import com.flamingos.osp.dao.ProfContactMapDAO;
import com.flamingos.osp.dao.ProfSpecializationAO;
import com.flamingos.osp.dao.ProfSubCategoryDAO;
import com.flamingos.osp.dao.ProfessionalDAO;
import com.flamingos.osp.dao.SignUpDAO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.service.SignUpService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;
import com.flamingos.osp.util.EncoderDecoderUtil;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class ProfessionalServiceImpl implements ProfessionalService {

	@Autowired
	private ProfessionalDAO profDAO;
	@Autowired
	private ContactDAO contactDAO;
	@Autowired
	private AddressDAO addressDAO;
	@Autowired
	private ProfAcademicsBeanDAO academicsDAO;
	@Autowired
	private ProfSpecializationAO specializationDAO;
	@Autowired
	private ProfSubCategoryDAO profSubCatDAO;
	@Autowired
	private ExperienceBeanDAO experienceDAO;
	@Autowired
	private ProfAddressMapDAO addressMapDAO;
	@Autowired
	private ProfContactMapDAO contactMapDAO;
	@Autowired
	private SignUpDAO signUpDAO;
	  @Autowired
	  private SignUpService signUpService;

	private static final Logger logger = Logger
			.getLogger(ProfessionalServiceImpl.class);

	@Value("${email.expire.time}")
	private int emailExpireTime;

	@Value("${sms.expire.time}")
	private int smsExpireTime;

	@Autowired
	EncoderDecoderUtil encDecUtil;

	@Autowired
	private ConfigParamBean configParamBean;

	ConfigParamDTO userStatusBean = null;

	@Override
	  public UserDTO verifyEmailDataAndUpdateStatus(String username, String UUID, String type)
	      throws OSPBusinessException {
	    logger.debug("Entrying ProfessionalService >> verifyEmailDataAndUpdateStatus method");
	    UserDTO userDto = new UserDTO();
	    try {
	      String decryptedUserName = encDecUtil.getDecodedValue(username);
	      UserBean user = new UserBean();
	      user.setUserName(decryptedUserName);
	      AccessToken access = new AccessToken();
	      access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
	      ConfigParamDTO oParamTokenUsed =
	              configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_TOKEN_STATUS,
	                  AppConstants.PARAM_NAME_ALREADY_USED);
	      user.setTokenIsUsed(oParamTokenUsed.getParameterid());
	      ConfigParamDTO oParamUserStatus =
	              configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_STATUS,
	                  AppConstants.PARAM_NAME_USER_STATUS_ACTIVE);
	      user.setActiveStatus(oParamUserStatus.getParameterid());
	      if (type.equals(AppConstants.EMAIL_TYPE)) {
	        user.setEmailUUID(UUID);
	        userDto = profDAO.getUserLinkValidCheckForEmail(user, access);
	        if (userDto != null) {
	          ConfigParamDTO oParamEmailStatus = configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_CONTACT_VERIFIED,AppConstants.PARAM_NAME_EMAIL_VERIFIED);
	          user.setEmailVerified(oParamEmailStatus.getParameterid());
	          profDAO.emailUpdateStatus(user, access);
	          userDto.setReturnMessage(AppConstants.LINK_VERFIED_MESSAGE);
	          logger.info(AppConstants.VALID);
	        } else {
	          userDto = new UserDTO();
	          userDto.setReturnMessage(AppConstants.INVALID_LINK);
	        }
	      } else {
	        if (type.equals(AppConstants.SMS_TYPE)) {
	          user.setSmsUUID(UUID);
	          userDto = profDAO.getUserLinkValidCheckForSms(user, access);
	          if (userDto != null) {
	      	    ConfigParamDTO oParamSMSStatus =configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_CONTACT_VERIFIED,AppConstants.PARAM_NAME_PHONE_VERIFIED);
	      	    user.setSmsVerfied(oParamSMSStatus.getParameterid());
	            profDAO.smsUpdateStatus(user, access);
	            userDto.setReturnMessage(AppConstants.LINK_VERFIED_MESSAGE);
	            logger.info(AppConstants.VALID);
	          } else {
	            userDto = new UserDTO();
	            userDto.setReturnMessage(AppConstants.INVALID_LINK);
	          }
	        }
	      }

	      return userDto;
	    } catch (OspDaoException exp) {
	      throw new OSPBusinessException(AppConstants.VERIFICATION_MODULE, "",
	          AppConstants.INVALID_LINK, exp);
	    } finally {
	      logger.debug("Entrying ProfessionalService << verifyEmailDataAndUpdateStatus method");
	    }

	  }


	 @Override
	  public String verifyAndGenerateNewToken(String username,HttpServletRequest request) throws OSPBusinessException {

	    logger.debug("Entrying ProfessionalService >> verifyAndGenerateNewToken() method");
	    try {
	      UserBean user = new UserBean();
	      String decryptedUserName = encDecUtil.getDecodedValue(username);
	      user.setUserName(decryptedUserName);
	      UserDTO userDt = signUpDAO.findByUserName(decryptedUserName);
	      if(userDt!=null)
	      {
	      AccessToken access = new AccessToken();
	      access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
	      ConfigParamDTO oParamEmailChannelEmail =
	          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
	              AppConstants.PARAM_NAME_EMAIL);
	      user.setTokenType(oParamEmailChannelEmail.getParameterid());
	      ConfigParamDTO oParamTokenUsed =
	              configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_TOKEN_STATUS,
	                  AppConstants.PARAM_NAME_NOT_YET_USED);
	      int not_yet_used = oParamTokenUsed.getParameterid();
	      oParamTokenUsed =
	              configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_TOKEN_STATUS,
	                  AppConstants.PARAM_NAME_ALREADY_USED);
	     
	     int already_used = oParamTokenUsed.getParameterid();
	     user.setTokenIsUsed(not_yet_used);
	      int userCount = profDAO.getTokenCheck(user, access);
	      if (userCount == 0) {
	        user.setTokenIsUsed(not_yet_used);
	        user.setActiveStatus(already_used);
	        profDAO.updateTokenStatus(user, access);       
	        user.setUser_id(userDt.getUserId());
	        user.setCommonUUID(String.valueOf(java.util.UUID.randomUUID()));
	        user.setTokenIsUsed(not_yet_used);
	        profDAO.generateNewToken(user, emailExpireTime);
	        signUpService.sendVerificationLinkinEmail(user, request);
	        ConfigParamDTO oParamEmailChannelSms =
	            configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
	                AppConstants.PARAM_NAME_SMS);
	        user.setTokenType(oParamEmailChannelSms.getParameterid());
	        UserDTO smsDTO = profDAO.getTokenCheckforSms(user, access);
	        if (null != smsDTO) {
	          user.setSmsUUID(smsDTO.getUserType());
	          user.setTokenIsUsed(already_used);
	          profDAO.updateTokenStatus(user, access);
	          user.setUser_id(userDt.getUserId());
	          user.setTokenIsUsed(not_yet_used);
	          user.setCommonUUID(String.valueOf(java.util.UUID.randomUUID()));
	          profDAO.generateNewToken(user, smsExpireTime);

	        }
	        return AppConstants.TOKEN_GENERATED;

	      } else {
	        return AppConstants.TOKEN_INVALID_MSG;
	      }      
	      }
	      else
	      {
	    	  return AppConstants.INVALID_LINK_MSG;  	  
	      }
	      
	    } catch (OspDaoException exp) {
	      throw new OSPBusinessException(AppConstants.TOKEN_GENERATED_FAIL, "", "", exp);
	    }catch (OspServiceException exp) {
	        throw new OSPBusinessException(AppConstants.TOKEN_GENERATED_FAIL, "", "", exp);
	      }
	    finally {
	      logger.debug("Exiting ProfessionalService << verifyAndGenerateNewToken() method");
	    }

	  }



	@Override
	public UserDTO verifyForgotPassword(String username, String UUID,
			String type) throws OSPBusinessException {
		logger.debug("Entrying ProfessionalService >> verifyForgotPassword() method....");
		UserDTO userDto = new UserDTO();
		try {

			String decryptedUserName = encDecUtil.getDecodedValue(username);
			UserBean user = new UserBean();
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
			user.setFupUUID(UUID);
			access.setType(0);
			userDto = profDAO.checkForForgotPassword(user, access);
			profDAO.FUPUpdateStatus(user, access);
			logger.info(AppConstants.VALID);
			userDto.setReturnStatus(AppConstants.SUCCESS);
			return userDto;
		} catch (EmptyResultDataAccessException exp) {
			userDto.setReturnStatus(AppConstants.FAILURE);
			userDto.setReturnMessage(AppConstants.INVALID_LINK);
			return userDto;
		} catch (OspDaoException exp) {
			throw new OSPBusinessException(AppConstants.VERIFICATION_MODULE,
					AppConstants.FUP_TOKEN_VERIFY_ERRCODE,
					AppConstants.INVALID_LINK);
		} finally {
			logger.debug("Exiting ProfessionalService << verifyForgotPassword() method....");
		}
	}

	@Override
	public UserDTO changePassword(UserBean userBean)
			throws OSPBusinessException {
		logger.debug("Entrying ProfessionalService >> changePassword() method....");
		try {
			userBean.setPassword(encDecUtil.getEncodedValue(userBean
					.getPassword()));
			profDAO.updatePassword(userBean);
			UserDTO user = new UserDTO();
			user.setReturnStatus(AppConstants.SUCCESS);
			user.setReturnMessage(AppConstants.CHANGE_PASSWORD_MESSAGE);
			return user;
		} catch (OspDaoException ex) {
			throw new OSPBusinessException(AppConstants.PASSWORD_CHANGE_MODULE,
					AppConstants.CHANGE_PASSWORD_ERRCODE,
					AppConstants.CHANGE_PASSWORD_ERRDESC, ex);

		} finally {
			logger.debug("Exiting ProfessionalService << changePassword() method....");
		}
	}

	@Override
	public void saveProfile(OspProfessionalBean professional,
			HttpServletRequest request) throws OSPBusinessException {
		try {
			String userId = AppUtil.trimLeadingTrailingQuote(request
					.getHeader("userId"));
			if (!StringUtils.isEmpty(userId)) {
				professional.setCreatedBy(userId);
				professional.setUpdatedBy(userId);
				if (professional.isDndStatus()) {
					professional.setDndActivatedFlag(0);
				} else {
					professional.setDndActivatedFlag(1);
				}
				if (professional.isEmailStatus()) {
					for (ConfigParamDTO param : configParamBean
							.getParamByCode(AppConstants.COMM_TEMPLATE_SUB_CATEGORY)) {
						if (professional.getProfSubscId() == null) {
							professional.setProfSubscId(String.valueOf(param
									.getParameterid()));
						} else {
							professional.setProfSubscId(professional
									.getProfSubscId()
									+ ":"
									+ String.valueOf(param.getParameterid()));
						}
					}

				}
				profDAO.saveProfile(professional);
				contactDAO.saveContact(professional);
				addressDAO.saveAddress(professional);
				addressMapDAO.saveAddressMap(professional);
				contactMapDAO.saveContactMap(professional);
				academicsDAO.saveAcademics(professional);
				profSubCatDAO.saveProfessionalSubCategory(professional);
				specializationDAO.saveSpecializations(professional);
				experienceDAO.saveExperience(professional);
				profDAO.saveProfAcheivements(professional);
				profDAO.saveProfRegMemNos(professional);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new OSPBusinessException(
					AppConstants.PROFESSIONAL_ADD_PROFILE_MODULE,
					AppConstants.PROFESSIONAL_ADD_PROFILE_EXCEPTION_ERRCODE,
					AppConstants.PROFESSIONAL_ADD_PROFILE_EXCEPTION_ERRDESC, ex);
		}
	}

	@Override
	public void approveProfile(OspProfessionalBean professional,
			HttpServletRequest request) throws OspServiceException {
		// TODO Auto-generated method stub
		userStatusBean = configParamBean.getParameterByCodeName(
				AppConstants.PARAM_CODE_USER_STATUS,
				AppConstants.PARAM_NAME_INITIAL);
		try {
			profDAO.approveProfile(professional, AppConstants.INT_ONE);
		} catch (OspDaoException ex) {
			throw new OspServiceException(ex);

		}
	}

	@Override
	public UserDTO verifyProfessional(String encryptedProfId)
			throws OSPBusinessException {
		logger.debug("Entrying ProfessionalService >> verifyForgotPassword() method....");
		try {
			String decryptedProfId = encDecUtil.getDecodedValue(encDecUtil
					.getEncodedValue("1"));
			UserBean user = new UserBean();
			user.setProf_id(Long.parseLong(decryptedProfId));
			UserDTO userDto = signUpDAO.checkForProfessional(user);
			if (null != userDto && userDto.getUserId() != 0) {
				logger.info(AppConstants.FAILURE);
				userDto.setReturnStatus(AppConstants.FAILURE);
				userDto.setReturnMessage(AppConstants.INVALID_LINK);
			} else {
				logger.info(AppConstants.VALID);
				userDto = new UserDTO();
				userDto.setReturnStatus(AppConstants.SUCCESS);
				userDto.setReturnMessage(AppConstants.LINK_VERFIED_MESSAGE);
			}

			return userDto;
		} catch (Exception exp) {
			throw new OSPBusinessException("", "", "", exp);

		} finally {
			logger.debug("Exiting ProfessionalService << verifyForgotPassword() method....");
		}
	}
	@Override
	  public OspProfessionalDTO professionalDetailsbyProfId(long profId) throws OSPBusinessException {
	    OspProfessionalDTO profDetails = null;
	    List<OspProfSpecializationBean> specializationList = null;
	    List<OspProfAcademicsBean> qualificationList = null;
	    List<OspExperienceBean> experienceList = null;
	    try {
	      specializationList = profDAO.getProfSpecializationList(profId);
	      qualificationList = profDAO.getProfQualificationList(profId);
	      experienceList = profDAO.getProfExperienceList(profId);
	      profDetails = profDAO.getProfessionalDetails(profId);
	      if (profDetails != null) {
	        profDetails.setExperienceList(experienceList);
	        profDetails.setQualificationList(qualificationList);
	        profDetails.setSpecializationList(specializationList);
	      }
	    } catch (OspDaoException exp) {
	      throw new OSPBusinessException(AppConstants.ADMIN_FETCH_PROFILE_MODULE,
	          AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRCODE,
	          AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRDESC, exp);

	    }
	    return profDetails;
	  }
	
	
	@Override
	  public OspProfessionalDTO professionalDetailsbyRecordID(long recordId) throws OSPBusinessException {
	    OspProfessionalDTO profDetails = null;
	    List<OspProfSpecializationBean> specializationList = null;
	    List<OspProfAcademicsBean> qualificationList = null;
	    List<OspExperienceBean> experienceList = null;
	    try {
	      profDetails=profDAO.getProfessionaDetailsByRecordId(recordId);
	      specializationList = profDAO.getProfSpecializationList(profDetails.getProfId());
	      qualificationList = profDAO.getProfQualificationList(profDetails.getProfId());
	      experienceList = profDAO.getProfExperienceList(profDetails.getProfId());
	      profDetails = profDAO.getProfessionalDetails(profDetails.getProfId());
	      if (profDetails != null) {
	        profDetails.setExperienceList(experienceList);
	        profDetails.setQualificationList(qualificationList);
	        profDetails.setSpecializationList(specializationList);
	      }
	    } catch (OspDaoException exp) {
	      throw new OSPBusinessException(AppConstants.ADMIN_FETCH_PROFILE_MODULE,
	          AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRCODE,
	          AppConstants.ADMIN_FETCH_PROFILE_MODULE_EXCEPTION_ERRDESC, exp);

	    }
	    return profDetails;
	  }
}
