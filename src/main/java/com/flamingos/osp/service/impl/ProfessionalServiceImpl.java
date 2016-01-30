package com.flamingos.osp.service.impl;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.AddressDao;
import com.flamingos.osp.dao.ContactDao;
import com.flamingos.osp.dao.ExperienceBeanDao;
import com.flamingos.osp.dao.ProfAcademicsBeanDao;
import com.flamingos.osp.dao.ProfAddressMapDao;
import com.flamingos.osp.dao.ProfContactMapDao;
import com.flamingos.osp.dao.ProfSpecializationDao;
import com.flamingos.osp.dao.ProfessionalDAO;
import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;
import com.flamingos.osp.service.ProfessionalService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.EncoderDecoderUtil;

@Transactional(propagation = Propagation.REQUIRED)
@Service
public class ProfessionalServiceImpl implements ProfessionalService {

  @Autowired
  private ProfessionalDAO profDao;
  @Autowired
  private ContactDao contactDao;
  @Autowired
  private AddressDao addressDao;
  @Autowired
  private ProfAcademicsBeanDao academicsDao;
  @Autowired
  private ProfSpecializationDao specializationDao;
  @Autowired
  private ExperienceBeanDao experienceDao;
  @Autowired
  private ProfAddressMapDao addressMapDao;
  @Autowired
  private ProfContactMapDao contactMapDao;
  @Autowired
  SignUpDao signUpDao;

  private static final Logger logger = Logger.getLogger(ProfessionalServiceImpl.class);

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
            if (type.equals(AppConstants.EMAIL_TYPE)) {
                user.setEmailUUID(UUID);
                access.setType(0);
                userDto = profDao.getUserLinkValidCheckForEmail(user, access);
                user.setActiveStatus(1);
                user.setEmailVerified(1);
                if (userDto != null) {
                    profDao.emailUpdateStatus(user, access);
                    userDto.setReturnMessage(AppConstants.LINK_VERFIED_MESSAGE);
                    logger.info(AppConstants.VALID);
                }
            } else {
                if (type.equals(AppConstants.SMS_TYPE)) {
                    user.setSmsUUID(UUID);
                    access.setType(1);
                    userDto = profDao.getUserLinkValidCheckForSms(user, access);
                    user.setActiveStatus(1);
                    if (userDto != null) {
                        profDao.smsUpdateStatus(user, access);
                        userDto.setReturnMessage(AppConstants.LINK_VERFIED_MESSAGE);
                        logger.info(AppConstants.VALID);
                    }
                }
            }

            return userDto;
        } catch (OspDaoException exp) {
            throw new OSPBusinessException(AppConstants.INVALID_LINK, "", "",exp);
        }finally
        {
        logger.debug("Entrying ProfessionalService << verifyEmailDataAndUpdateStatus method");
        }

    }

   @Override
	public String verifyAndGenerateNewToken(String username, String UUID)
			throws OSPBusinessException {

		logger.debug("Entrying ProfessionalService >> verifyAndGenerateNewToken() method");
		try {
			UserBean user = new UserBean();
			String decryptedUserName = encDecUtil.getDecodedValue(username);
			user.setUserName(decryptedUserName);
			AccessToken access = new AccessToken();
			access.setExpireTime(new Timestamp(new java.util.Date().getTime()));
                        ConfigParamDTO oParamEmailChannelEmail =
				          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
				              AppConstants.PARAM_NAME_EMAIL);
				  user.setTokenType(oParamEmailChannelEmail.getParameterid());
			int userCount= profDao.getTokenCheck(user, access);
			if (userCount == 0) {
				user.setEmailUUID(UUID);
				profDao.emailUpdateStatus(user, access);
				UserDTO userDt = signUpDao.findByUserName(decryptedUserName);
				user.setUser_id(userDt.getUserId());
				user.setCommonUUID(String.valueOf(java.util.UUID.randomUUID()));				 
					profDao.generateNewToken(user, emailExpireTime);					
					  ConfigParamDTO oParamEmailChannelSms =
					          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
					              AppConstants.PARAM_NAME_SMS);
					  user.setTokenType(oParamEmailChannelSms.getParameterid());
					  UserDTO smsDTO = profDao.getTokenCheckforSms(user, access);
					  if(null!=smsDTO)
					  {	 user.setSmsUUID(smsDTO.getUserType());
						  profDao.smsUpdateStatus(user, access);
						  user.setUser_id(userDt.getUserId());
						  profDao.generateNewToken(user, smsExpireTime);  
						  
					  }
						return AppConstants.SUCCESS;
					 
				} else {
				return AppConstants.INVALID_LINK;
				}
		} catch (OspDaoException exp) {
			throw new OSPBusinessException(AppConstants.TOKEN_GENERATED_FAIL, "", "",exp);
		}finally
                {
                logger.debug("Exiting ProfessionalService << verifyAndGenerateNewToken() method");
                }

	}


    @Override
    public UserDTO verifyForgotPassword(String username, String UUID, String type)
            throws OSPBusinessException {
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
            userDto = profDao.checkForForgotPassword(user, access);
            profDao.FUPUpdateStatus(user, access);
            logger.info(AppConstants.VALID);
            userDto.setReturnStatus(AppConstants.SUCCESS);
            return userDto;
        } catch (EmptyResultDataAccessException exp) {
            userDto.setReturnStatus(AppConstants.FAILURE);
            userDto.setReturnMessage(AppConstants.INVALID_LINK);
            return userDto;
        } catch (OspDaoException exp) {
            throw new OSPBusinessException(AppConstants.VERIFICATION_MODULE,
                    AppConstants.FUP_TOKEN_VERIFY_ERRCODE, AppConstants.INVALID_LINK);
        }finally
        {
        logger.debug("Exiting ProfessionalService << verifyForgotPassword() method....");
          }
        }

    @Override
    public UserDTO changePassword(UserBean userBean) throws OSPBusinessException {
       logger.debug("Entrying ProfessionalService >> changePassword() method....");
        try {
            userBean.setPassword(encDecUtil.getEncodedValue(userBean.getPassword()));
            profDao.updatePassword(userBean);
            UserDTO user = new UserDTO();
            user.setReturnStatus(AppConstants.SUCCESS);
            user.setReturnMessage(AppConstants.CHANGE_PASSWORD_MESSAGE);
            return user;
        } catch (OspDaoException ex) {
            throw new OSPBusinessException(AppConstants.PASSWORD_CHANGE_MODULE,
                    AppConstants.CHANGE_PASSWORD_ERRCODE, AppConstants.CHANGE_PASSWORD_ERRDESC,ex);

        }finally
        {
        logger.debug("Exiting ProfessionalService << changePassword() method....");
        }
      }




  @Override
  public String saveProfile(OspProfessionalBean professional, HttpServletRequest request)
      throws OSPBusinessException {
    try {
      profDao.saveProfile(professional);
      contactDao.saveContact(professional);
      addressDao.saveAddress(professional);
      addressMapDao.saveAddressMap(professional);
      contactMapDao.saveContactMap(professional);
      academicsDao.saveAcademics(professional.getQualificationList());
      specializationDao.saveSpecializations(professional.getSpecializationList());
      experienceDao.saveExperience(professional.getExperienceList());
    } catch (Exception ex) {
      throw new OSPBusinessException(AppConstants.PROFESSIONAL_ADD_PROFILE_MODULE,
          AppConstants.PROFESSIONAL_ADD_PROFILE_EXCEPTION_ERRCODE,
          AppConstants.PROFESSIONAL_ADD_PROFILE_EXCEPTION_ERRDESC, ex);
    }
    return null;
  }

  @Override
  public String approveProfile(OspProfessionalBean professional, HttpServletRequest request)
      throws OspServiceException {
    // TODO Auto-generated method stub
    userStatusBean =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_STATUS,
            AppConstants.PARAM_NAME_INITIAL);
    try {
      profDao.approveProfile(professional, 1);
    } catch (OspDaoException ex) {
      throw new OspServiceException(ex);

    }

    return null;
  }

}
