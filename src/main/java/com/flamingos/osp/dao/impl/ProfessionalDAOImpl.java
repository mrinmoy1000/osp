package com.flamingos.osp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.OspAddressBean;
import com.flamingos.osp.bean.OspContactBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.ProfessionalDAO;
import com.flamingos.osp.dto.OspProfessionalDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ProfessionalDAOImpl implements ProfessionalDAO {

  private static final Logger logger = Logger.getLogger(ProfessionalDAOImpl.class);
  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Autowired
  private ConfigParamBean configParamBean;
  
  @Value("${query_osp_professional_insert}")
  private String SQL_INSERT_OSP_PROFESSIONAL;
  
  @Override
  public void emailUpdateStatus(UserBean user, AccessToken access) throws OspDaoException {

    logger.debug("Entrying ProfessionalDao >> emailUpdateStatus() method");

    String updateEmailStatusSql =
        " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up " + "  SET " + " up."
            + AppConstants.ACTIVATION_STATUS + "= :" + AppConstants.ACTIVATION_STATUS + ", up."
            + AppConstants.EMAIL_VERIFIED + " = :" + AppConstants.EMAIL_VERIFIED + " , up."
            + AppConstants.LOGIN_TS + " = :" + AppConstants.LOGIN_TS + " , acc."
            + AppConstants.TOKEN_EXPIRY_DT + " = :" + AppConstants.TOKEN_EXPIRY_DT + " where up."
            + AppConstants.USER_NAME + " = :" + AppConstants.USER_NAME + " and acc."
            + AppConstants.UUID + " = :" + AppConstants.UUID + " and  up." + AppConstants.RECORD_ID
            + " = acc." + AppConstants.RECORD_ID;
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put(AppConstants.ACTIVATION_STATUS, 0);
    paramMap.put(AppConstants.EMAIL_VERIFIED, user.getEmailVerified());
    paramMap.put(AppConstants.LOGIN_TS, new Timestamp(new Date().getTime()));
    paramMap.put(AppConstants.TOKEN_EXPIRY_DT, access.getExpireTime());
    paramMap.put(AppConstants.USER_NAME, user.getUserName());
    paramMap.put(AppConstants.UUID, user.getEmailUUID());
    namedJdbcTemplate.update(updateEmailStatusSql, paramMap);
    logger.debug("Exiting ProfessionalDao << emailUpdateStatus() method");
  }

  @Override
  public long saveProfile(OspProfessionalBean professionalBean) throws OspDaoException {
    logger.debug("Entering ProfessionalDao >> saveProfile() method");
    try {
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("RECORD_ID", professionalBean.getRecordId());
      namedParameters.addValue("PROF_FIRST_NAME", professionalBean.getProfFirstName());
      namedParameters.addValue("PROF_MIDDLE_NAME", professionalBean.getProfMiddleName());
      namedParameters.addValue("PROF_LAST_NAME", professionalBean.getProfLastName());
      namedParameters.addValue("PROF_EMP_ID", professionalBean.getProfEmpId());
      namedParameters.addValue("PROF_DOB", new Date());
      namedParameters.addValue("PROF_GENDER", professionalBean.getProfGender());
      namedParameters.addValue("PROF_NATIONALITY", professionalBean.getProfNationality());
      namedParameters.addValue("PROF_PAN", professionalBean.getProfPan());
      namedParameters.addValue("PROF_MERITAL_STATUS", professionalBean.getProfMeritalStatus());
      namedParameters.addValue("PROF_MERRIAGE_ANNIVERSARY", new Date());
      namedParameters.addValue("DND_ACTIVATED_FLAG", professionalBean.getDndActivatedFlag());
      namedParameters.addValue("PROF_SIGNATURE", professionalBean.getProfSignature());
      namedParameters.addValue("PROF_SUBSC_ID", professionalBean.getProfSubscId());
      namedParameters.addValue("PROF_PUBLIC_ID", professionalBean.getProfPublicId());
      namedParameters.addValue("PROF_FEES", professionalBean.getProfFees());
      namedParameters.addValue("PROF_REMARK", professionalBean.getProfRemark());
      namedParameters.addValue("STATUS", professionalBean.getStatus());
      namedParameters.addValue("CREATED_TS", new Timestamp(new Date().getTime()));
      namedParameters.addValue("CREATED_BY", professionalBean.getCreatedBy());
      logger.info("ProfessionalDao.saveProfile() query" + SQL_INSERT_OSP_PROFESSIONAL);
      namedJdbcTemplate.update(SQL_INSERT_OSP_PROFESSIONAL, namedParameters, generatedKeyHolder);

      professionalBean.setProfId(generatedKeyHolder.getKey().longValue());
      return professionalBean.getProfId();
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    } finally {
      logger.debug("Exiting ProfessionalDao << saveProfile() method");
    }
  }

  @Override
  public void approveProfile(OspProfessionalBean professionalBean, int param_id)
      throws OspDaoException {
    logger.debug("Entering ProfessionalDao >> approveProfile() method");
    try {
      int count = 0;
      int initalStatusCode =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_STATUS,
              AppConstants.PARAM_NAME_INITIAL).getParameterid();

      int actionStatusCode = 0;
      if (!StringUtils.isEmpty(professionalBean.getActionTaken())) {
        actionStatusCode =
            configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_STATUS,
                professionalBean.getActionTaken()).getParameterid();

        String updateStatus =
            " UPDATE  OSP_PROFESSIONAL opp" + "  SET opp." + AppConstants.USER_STATUS + "= ? "
                + "where " + AppConstants.PROF_ID + " = ? " + " and " + AppConstants.USER_STATUS
                + "=?";

        count =
            jdbcTemplate.update(updateStatus,
                new Object[] {actionStatusCode, professionalBean.getProfId(), initalStatusCode});
      }
      if (count != 1) {
        throw new OspDaoException(AppConstants.ADMIN_APPROVE_PROFILE_MODULE_EXCEPTION_ERRDESC);
      }
    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);
    } finally {
      logger.debug("Exiting ProfessionalDao << approveProfile() method");
    }
  }

  @Override
  public void smsUpdateStatus(UserBean user, AccessToken access) throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> smsUpdateStatus() method");
    String updateSmsStatusSql =
        " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up " + "  SET " + " up."
            + AppConstants.ACTIVATION_STATUS + "= :" + AppConstants.ACTIVATION_STATUS + " , up."
            + AppConstants.SMS_VERIFIED + " = :" + AppConstants.SMS_VERIFIED + " , up."
            + AppConstants.LOGIN_TS + " = :" + AppConstants.LOGIN_TS + " , acc."
            + AppConstants.TOKEN_EXPIRY_DT + " = :" + AppConstants.TOKEN_EXPIRY_DT + " where up."
            + AppConstants.USER_NAME + " = :" + AppConstants.USER_NAME + " and acc."
            + AppConstants.UUID + " = :" + AppConstants.UUID + " and  up." + AppConstants.RECORD_ID
            + " = acc." + AppConstants.RECORD_ID;
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put(AppConstants.ACTIVATION_STATUS, 0);
    paramMap.put(AppConstants.SMS_VERIFIED, 0);
    paramMap.put(AppConstants.LOGIN_TS, new Timestamp(new Date().getTime()));
    paramMap.put(AppConstants.TOKEN_EXPIRY_DT, access.getExpireTime());
    paramMap.put(AppConstants.USER_NAME, user.getUserName());
    paramMap.put(AppConstants.UUID, user.getSmsUUID());
    namedJdbcTemplate.update(updateSmsStatusSql, paramMap);
    logger.debug("Exiting ProfessionalDao << smsUpdateStatus() method");
  }

  @Override
  public void FUPUpdateStatus(UserBean user, AccessToken access) throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> FUPUpdateStatus() method");
    String updateEmailStatusSql =
        " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up " + "  SET " + "  up."
            + AppConstants.LOGIN_TS + " = :" + AppConstants.LOGIN_TS + " , acc."
            + AppConstants.TOKEN_EXPIRY_DT + " = :" + AppConstants.TOKEN_EXPIRY_DT + " where up."
            + AppConstants.USER_NAME + " = :" + AppConstants.USER_NAME + " and acc."
            + AppConstants.UUID + " = :" + AppConstants.UUID + " and  up." + AppConstants.RECORD_ID
            + " = acc." + AppConstants.RECORD_ID;
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put(AppConstants.LOGIN_TS, new Timestamp(new Date().getTime()));
    paramMap.put(AppConstants.TOKEN_EXPIRY_DT, access.getExpireTime());
    paramMap.put(AppConstants.USER_NAME, user.getUserName());
    paramMap.put(AppConstants.UUID, user.getFupUUID());
    namedJdbcTemplate.update(updateEmailStatusSql, paramMap);
    logger.debug("Exiting ProfessionalDao << FUPUpdateStatus() method");
  }

  @Override
  public UserDTO getUserLinkValidCheckForEmail(UserBean user, AccessToken access)
      throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> getUserLinkValidCheckForEmail() method");
    try {
      String emailSql =
          "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN " + " acc  where" + " up."
              + AppConstants.USER_NAME + "= :user_name and " + " acc." + AppConstants.UUID
              + "=:UUID and" + "  acc." + AppConstants.TOKEN_EXPIRY_DT + "> :EXPIRY_DT"
              + " and  acc." + AppConstants.RECORD_ID + "= up." + AppConstants.RECORD_ID;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("UUID", user.getEmailUUID());
      paramMap.put("EXPIRY_DT", access.getExpireTime());

      return namedJdbcTemplate.queryForObject(emailSql, paramMap, new RowMapper<UserDTO>() {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.RECORD_ID));
          user.setUserName(rs.getString(AppConstants.USER_NAME));
          user.setUserPass(rs.getString(AppConstants.PASSWORD));
          user.setUserContact(rs.getString(AppConstants.CONTACT_NUMBER));
          user.setEmail(rs.getString(AppConstants.EMAIL));
          user.setActivationStatus(rs.getString(AppConstants.ACTIVATION_STATUS));

          return user;
        }
      });

    } catch (EmptyResultDataAccessException e) {
      logger.error("no record found . Explicitly throwing exception", e);
      return null;
    } finally {
      logger.debug("Entrying ProfessionalDao << getUserLinkValidCheckForEmail() method");
    }
  }

  @Override
  public UserDTO getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
      throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> getUserLinkValidCheckForFUP() method");
    try {
      String emailSql =
          "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN " + " acc  where" + " up."
              + AppConstants.USER_NAME + "= :user_name and " + " acc." + AppConstants.UUID
              + "=:UUID and" + " and  acc." + AppConstants.TOKEN_EXPIRY_DT + "> :EXPIRY_DT"
              + " and  acc." + AppConstants.USER_ID + "= up." + AppConstants.RECORD_ID;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("UUID", user.getFupUUID());
      paramMap.put("EXPIRY_DT", access.getExpireTime());

      return namedJdbcTemplate.queryForObject(emailSql, paramMap, new RowMapper<UserDTO>() {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.RECORD_ID));
          user.setUserName(rs.getString(AppConstants.USER_NAME));
          user.setUserPass(rs.getString(AppConstants.PASSWORD));
          user.setUserContact(rs.getString(AppConstants.CONTACT_NUMBER));
          user.setEmail(rs.getString(AppConstants.EMAIL));
          user.setActivationStatus(rs.getString(AppConstants.ACTIVATION_STATUS));

          return user;
        }
      });

    } catch (EmptyResultDataAccessException e) {
      logger.error("no record found . Explicitly throwing exception", e);
      return null;
    } finally {
      logger.debug("Entrying ProfessionalDao << getUserLinkValidCheckForFUP() method");
    }
  }

  @Override
  public UserDTO getUserLinkValidCheckForSms(UserBean user, AccessToken access)
      throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> getUserLinkValidCheckForSms() method");
    try {
      String emailSql =
          "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN " + " acc  where" + " up."
              + AppConstants.USER_NAME + "= :user_name and " + " acc." + AppConstants.UUID
              + "=:UUID " + " and  acc." + AppConstants.TOKEN_EXPIRY_DT + "> :EXPIRY_DT"
              + " and  acc." + AppConstants.RECORD_ID + "= up." + AppConstants.RECORD_ID;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("UUID", user.getSmsUUID());
      paramMap.put("EXPIRY_DT", access.getExpireTime());

      return namedJdbcTemplate.queryForObject(emailSql, paramMap, new RowMapper<UserDTO>() {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.RECORD_ID));
          user.setUserName(rs.getString(AppConstants.USER_NAME));
          user.setUserPass(rs.getString(AppConstants.PASSWORD));
          user.setUserContact(rs.getString(AppConstants.CONTACT_NUMBER));
          user.setEmail(rs.getString(AppConstants.EMAIL));
          user.setActivationStatus(rs.getString(AppConstants.ACTIVATION_STATUS));

          return user;
        }
      });
    } catch (EmptyResultDataAccessException e) {
      logger.error("no record found . Explicitly throwing exception", e);
      return null;
    } finally {
      logger.debug("Entrying ProfessionalDao << getUserLinkValidCheckForSms() method");
    }
  }

  @Override
  public int getTokenCheck(UserBean user, AccessToken access) throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> getTokenCheck() method");
    try {

      String emailSql =
          "select count(up.RECORD_ID) from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN acc where"
              + " up." + AppConstants.USER_NAME + "= :user_name and " + " acc."
              + AppConstants.TOKEN_EXPIRY_DT + "> :EXPIRY_DT" + " and" + "  acc."
              + AppConstants.RECORD_ID + "= up." + AppConstants.RECORD_ID + " and " + "acc."
              + AppConstants.TYPE + "=:" + AppConstants.TYPE;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("EXPIRY_DT", access.getExpireTime());
      paramMap.put(AppConstants.TYPE, user.getTokenType());

      return namedJdbcTemplate.queryForInt(emailSql, paramMap);
    } catch (EmptyResultDataAccessException e) {
      logger.error("no record found . Explicitly throwing exception", e);
      return 0;
    } finally {
      logger.debug("Entrying ProfessionalDao << getTokenCheck() method");
    }

  }

  @Override
  public void generateNewToken(UserBean user, int ExpireTime) throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> generateNewToken() method");
    String insertAccessToken =
        "INSERT INTO OSP_ACCESS_TOKEN VALUES " + "(:" + AppConstants.USER_ID + "," + ":"
            + AppConstants.TYPE + "," + ":" + AppConstants.UUID + "," + ":"
            + AppConstants.TOKEN_EXPIRY_DT + "," + ":" + AppConstants.IS_USED + "," + ":"
            + AppConstants.CREATED_TS + "," + ":" + AppConstants.UPDATE_TS + "," + ":"
            + AppConstants.CREATED_BY + "," + ":" + AppConstants.UPDATE_BY + ");";

    Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
    accessTokenMapforEmail.put(AppConstants.USER_ID, user.getUser_id());
    accessTokenMapforEmail.put(AppConstants.TYPE, user.getTokenType());
    accessTokenMapforEmail.put(AppConstants.UUID, user.getCommonUUID());
    accessTokenMapforEmail.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime()
        + (1 * (ExpireTime) * 60 * 60 * 1000)));
    accessTokenMapforEmail.put(AppConstants.IS_USED, 0);
    accessTokenMapforEmail.put(AppConstants.CREATED_TS, new Timestamp(new Date().getTime()));
    accessTokenMapforEmail.put(AppConstants.UPDATE_TS, null);
    accessTokenMapforEmail.put(AppConstants.CREATED_BY, user.getUserName());
    accessTokenMapforEmail.put(AppConstants.UPDATE_BY, null);

    namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
    logger.debug("Exiting ProfessionalDao << generateNewToken() method");

  }

  @Override
  public UserDTO checkForForgotPassword(UserBean user, AccessToken access) throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> checkForForgotPassword() method");
    try {
      String emailSql =
          "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN " + " acc  where" + " up."
              + AppConstants.USER_NAME + "= :user_name and " + " acc." + AppConstants.UUID
              + "=:UUID and" + " acc." + AppConstants.TOKEN_EXPIRY_DT + "> :EXPIRY_DT"
              + " and  acc." + AppConstants.RECORD_ID + "= up." + AppConstants.RECORD_ID;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("UUID", user.getFupUUID());
      paramMap.put("EXPIRY_DT", access.getExpireTime());

      return namedJdbcTemplate.queryForObject(emailSql, paramMap, new RowMapper<UserDTO>() {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.RECORD_ID));
          return user;
        }
      });
    } catch (EmptyResultDataAccessException e) {
      logger.error("no record found . Explicitly throwing exception", e);
      throw null;
    } finally {
      logger.debug("Exiting ProfessionalDao << checkForForgotPassword() method");
    }

  }

  @Override
  public void updatePassword(UserBean user) throws OspDaoException {
    logger.debug("Entrying ProfessionalDao >> updatePassword() method");
    String updatePassword =
        " UPDATE  OSP_USER_CREDENTIAL up " + "  SET up." + AppConstants.PASSWORD + " = :"
            + AppConstants.PASSWORD + ", up." + AppConstants.LOGIN_TS + " = :"
            + AppConstants.LOGIN_TS + " where  up." + AppConstants.RECORD_ID + " =  :"
            + AppConstants.RECORD_ID;
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put(AppConstants.PASSWORD, user.getPassword());
    paramMap.put(AppConstants.LOGIN_TS, new Timestamp(new Date().getTime()));
    paramMap.put(AppConstants.RECORD_ID, user.getUser_id());
    namedJdbcTemplate.update(updatePassword, paramMap);
    logger.debug("Exiting ProfessionalDao << updatePassword() method");
  }

  @Override
  public long addProfile(OspProfessionalBean professionalBean) throws OspDaoException {
    String sql =
        "INSERT INTO OSP_PROFESSIONAL(RECORD_ID,PROF_FIRST_NAME,PROF_MIDDLE_NAME,PROF_LAST_NAME,PROF_EMP_ID,PROF_DOB,PROF_GENDER,PROF_NATIONALITY,PROF_PAN,PROF_MERITAL_STATUS,PROF_MERRIAGE_ANNIVERSARY,DND_ACTIVATED_FLAG,PROF_SIGNATURE,PROF_SUBSC_ID,PROF_PUBLIC_ID,PROF_FEES,PROF_REMARK,STATUS,CREATED_TS,CREATED_BY) VALUES(:RECORD_ID, :PROF_FIRST_NAME, :PROF_MIDDLE_NAME, :PROF_LAST_NAME, :PROF_EMP_ID, :PROF_DOB, :PROF_GENDER, :PROF_NATIONALITY, :PROF_PAN, :PROF_MERITAL_STATUS, :PROF_MERRIAGE_ANNIVERSARY, :DND_ACTIVATED_FLAG, :PROF_SIGNATURE, :PROF_SUBSC_ID, :PROF_PUBLIC_ID, :PROF_FEES, :PROF_REMARK, :STATUS, :CREATED_TS, :CREATED_BY)";
    try {
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("RECORD_ID", professionalBean.getRecordId());
      namedParameters.addValue("PROF_FIRST_NAME", professionalBean.getProfFirstName());
      namedParameters.addValue("PROF_MIDDLE_NAME", professionalBean.getProfMiddleName());
      namedParameters.addValue("PROF_LAST_NAME", professionalBean.getProfLastName());
      namedParameters.addValue("PROF_EMP_ID", professionalBean.getProfEmpId());
      namedParameters.addValue("PROF_DOB", new Date());
      namedParameters.addValue("PROF_GENDER", professionalBean.getProfGender());
      namedParameters.addValue("PROF_NATIONALITY", professionalBean.getProfNationality());
      namedParameters.addValue("PROF_PAN", professionalBean.getProfPan());
      namedParameters.addValue("PROF_MERITAL_STATUS", professionalBean.getProfMeritalStatus());
      namedParameters.addValue("PROF_MERRIAGE_ANNIVERSARY", new Date());
      namedParameters.addValue("DND_ACTIVATED_FLAG", professionalBean.getDndActivatedFlag());
      namedParameters.addValue("PROF_SIGNATURE", professionalBean.getProfSignature());
      namedParameters.addValue("PROF_SUBSC_ID", professionalBean.getProfSubscId());
      namedParameters.addValue("PROF_PUBLIC_ID", professionalBean.getProfPublicId());
      namedParameters.addValue("PROF_FEES", professionalBean.getProfFees());
      namedParameters.addValue("PROF_REMARK", professionalBean.getProfRemark());
      namedParameters.addValue("STATUS", professionalBean.getStatus());
      namedParameters.addValue("CREATED_TS", new Timestamp(new Date().getTime()));
      namedParameters.addValue("CREATED_BY", professionalBean.getCreatedBy());

      namedJdbcTemplate.update(sql, namedParameters, generatedKeyHolder);

      professionalBean.setProfId(generatedKeyHolder.getKey().longValue());
      return professionalBean.getProfId();
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }
  }

  @Override
  public List<OspProfessionalDTO> getAllProfessionalDetails() throws OspDaoException {

    List<OspProfessionalDTO> professionalDetailList = null;
    try {

      String getProSql = "select * from OSP_PROFESSIONAL op where  op.PROF_ID = ope.PROF_ID";

      professionalDetailList =
          namedJdbcTemplate.query(getProSql, new RowMapper<OspProfessionalDTO>() {
            @Override
            public OspProfessionalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
              OspProfessionalDTO prof = new OspProfessionalDTO();

              prof.setProfId(rs.getInt(1));
              prof.setRecordId(rs.getInt(2));
              prof.setProfFirstName(rs.getString(3));
              prof.setProfMiddleName(rs.getString(4));
              prof.setProfLastName(rs.getString(5));
              prof.setProfEmpId(rs.getString(6));
              prof.setProfDob(rs.getDate(7));
              prof.setProfGender(rs.getInt(8));
              prof.setProfNationality(rs.getString(9));
              prof.setProfPan(rs.getString(10));
              prof.setProfMeritalStatus(rs.getInt(11));
              prof.setProfMerriageAnniversary(rs.getDate(12));
              prof.setDndActivatedFlag(rs.getInt(13));
              prof.setProfSignature(rs.getBytes(14));
              prof.setProfPhotograph(rs.getString(15));
              prof.setProfSubscId(rs.getString(16));
              prof.setProfPublicId(rs.getString(17));
              prof.setProfFees(rs.getDouble(18));
              prof.setProfRemark(rs.getString(19));
              prof.setStatus(rs.getInt(20));
              prof.setCreatedBy(rs.getString(21));
              prof.setCreatedTs(rs.getTimestamp(22));


              prof.setAddressList(fetchAddressDetails(prof.getProfId()));
              prof.setContactList(fetchContactDetails(prof.getProfId()));

              return prof;
            }
          });

    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);

    }
    return professionalDetailList;
  }

  @Override
  public OspProfessionalDTO getProfessionalDetails(long profId) throws OspDaoException {

    OspProfessionalDTO professionalDetail = null;

    try {

      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("prof_id", profId);

      String getProSql = "select * from OSP_PROFESSIONAL op where op.PROF_ID = :prof_id ";

      professionalDetail =
          namedJdbcTemplate.queryForObject(getProSql, paramMap,
              new RowMapper<OspProfessionalDTO>() {
                @Override
                public OspProfessionalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                  OspProfessionalDTO prof = new OspProfessionalDTO();

                  prof.setProfId(rs.getInt(1));
                  prof.setRecordId(rs.getInt(2));
                  prof.setProfFirstName(rs.getString(3));
                  prof.setProfMiddleName(rs.getString(4));
                  prof.setProfLastName(rs.getString(5));
                  prof.setProfEmpId(rs.getString(6));
                  prof.setProfDob(rs.getDate(7));
                  prof.setProfGender(rs.getInt(8));
                  prof.setProfNationality(rs.getString(9));
                  prof.setProfPan(rs.getString(10));
                  prof.setProfMeritalStatus(rs.getInt(11));
                  prof.setProfMerriageAnniversary(rs.getDate(12));
                  prof.setDndActivatedFlag(rs.getInt(13));
                  prof.setProfSignature(rs.getBytes(14));
                  prof.setProfPhotograph(rs.getString(15));
                  prof.setProfSubscId(rs.getString(16));
                  prof.setProfPublicId(rs.getString(17));
                  prof.setProfFees(rs.getDouble(18));
                  prof.setProfRemark(rs.getString(19));
                  prof.setStatus(rs.getInt(20));
                  prof.setCreatedBy(rs.getString(21));
                  prof.setCreatedTs(rs.getTimestamp(22));


                  prof.setAddressList(fetchAddressDetails(prof.getProfId()));
                  prof.setContactList(fetchContactDetails(prof.getProfId()));

                  return prof;
                }
              });

    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);

    }
    return professionalDetail;
  }

  @Override
  public List<OspProfSpecializationBean> getProfSpecializationList(long profId)
      throws OspDaoException {
    List<OspProfSpecializationBean> specializationList = null;

    String getSepecialzationSql =
        "select ops.PROF_SPEC_ID, ops.PROF_SPEC_NAME, ops.PROF_SPEC_DESC, ops.PROF_ID, "
            + "ops.ACTIVE_STATUS, ops.CREATED_BY, ops.CREATED_TS"
            + " from OSP_PROFESSIONAL op, OSP_PROF_SPECIALIZATIONS ops"
            + " where op.PROF_ID = ops.PROF_ID" + " and op.PROF_ID = :prof_id ";

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("prof_id", profId);
    try {
      specializationList =
          namedJdbcTemplate.query(getSepecialzationSql, paramMap,
              new RowMapper<OspProfSpecializationBean>() {
                @Override
                public OspProfSpecializationBean mapRow(ResultSet rs, int i) throws SQLException {
                  OspProfSpecializationBean ospProfSpecializationBean =
                      new OspProfSpecializationBean();
                  ospProfSpecializationBean.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
                  ospProfSpecializationBean.setProfId(rs.getInt("PROF_ID"));
                  ospProfSpecializationBean.setProfSpecDesc(rs.getString("PROF_SPEC_DESC"));
                  ospProfSpecializationBean.setProfSpecName(rs.getString("PROF_SPEC_NAME"));
                  ospProfSpecializationBean.setProfSpecId(rs.getInt("PROF_SPEC_ID"));
                  return ospProfSpecializationBean;
                }
              });
    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);

    }
    return specializationList;
  }

  @Override
  public List<OspProfAcademicsBean> getProfQualificationList(long profId) throws OspDaoException {
    List<OspProfAcademicsBean> qualificationList = null;
    String getAcademicsSql =
        "select opa.ACTIVE_STATUS, opa.PROF_ACDMC_DESC, "
            + "opa.PROF_ACDMC_ID, opa.PROF_ACDMC_NAME, opa.PROF_ACDMC_PASS_YEAR, opa.PROF_ACDMC_UNIVERSITY"
            + " from OSP_PROFESSIONAL op, OSP_PROF_ACADEMICS opa"
            + " where op.PROF_ID = opa.PROF_ID" + " and op.PROF_ID = :prof_id ";

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("prof_id", profId);
    try {
      qualificationList =
          namedJdbcTemplate.query(getAcademicsSql, paramMap, new RowMapper<OspProfAcademicsBean>() {
            @Override
            public OspProfAcademicsBean mapRow(ResultSet rs, int i) throws SQLException {
              OspProfAcademicsBean ospProfAcademicsBean = new OspProfAcademicsBean();
              ospProfAcademicsBean.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
              ospProfAcademicsBean.setProfAcdmcDesc(rs.getString("PROF_ACDMC_DESC"));
              ospProfAcademicsBean.setProfAcdmcId(rs.getInt("PROF_ACDMC_ID"));
              ospProfAcademicsBean.setProfAcdmcName(rs.getString("PROF_ACDMC_NAME"));
              ospProfAcademicsBean.setProfAcdmcPassYear(rs.getString("PROF_ACDMC_PASS_YEAR"));
              ospProfAcademicsBean.setProfAcdmcUniversity(rs.getString("PROF_ACDMC_UNIVERSITY"));
              return ospProfAcademicsBean;
            }
          });
    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);
    }

    return qualificationList;
  }

  @Override
  public List<OspExperienceBean> getProfExperienceList(long profId) throws OspDaoException {
    List<OspExperienceBean> experienceList = null;
    String getExperienceSql =
        "select ope.ACTIVE_STATUS, ope.PROF_EXP_BEGIN_DT, "
            + "ope.PROF_EXP_DESC, ope.PROF_EXP_END_DT, ope.PROF_EXP_ID"
            + " from OSP_PROFESSIONAL op, OSP_PROF_EXPERIENCE ope"
            + " where op.PROF_ID = ope.PROF_ID" + " and op.PROF_ID = :prof_id ";
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("prof_id", profId);
    try {
      experienceList =
          namedJdbcTemplate.query(getExperienceSql, paramMap, new RowMapper<OspExperienceBean>() {
            @Override
            public OspExperienceBean mapRow(ResultSet rs, int i) throws SQLException {
              OspExperienceBean ospExperienceBean = new OspExperienceBean();
              ospExperienceBean.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
              ospExperienceBean.setProfExpBeginDt(rs.getDate("PROF_EXP_BEGIN_DT"));
              ospExperienceBean.setProfExpEndDt(rs.getDate("PROF_EXP_END_DT"));
              ospExperienceBean.setProfExpDesc(rs.getString("PROF_EXP_DESC"));
              ospExperienceBean.setProfExpId(rs.getInt("PROF_EXP_ID"));
              return ospExperienceBean;
            }
          });
    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);
    }

    return experienceList;
  }

  @Override
  public UserDTO getTokenCheckforSms(UserBean user, AccessToken access) throws OspDaoException {
    try {
      logger.debug("Entrying ProfessionalDao >> getTokenCheckforSms() method");
      String emailSql =
          "select * from  OSP_USER_CREDENTIAL up ,OSP_ACCESS_TOKEN acc  where  up."
              + AppConstants.USER_NAME + "= :user_name  " + " and  acc."
              + AppConstants.TOKEN_EXPIRY_DT + "< :EXPIRY_DT" + " and  acc."
              + AppConstants.RECORD_ID + "= up." + AppConstants.RECORD_ID + " and acc."
              + AppConstants.TYPE + "= :" + AppConstants.TYPE;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("EXPIRY_DT", access.getExpireTime());
      paramMap.put(AppConstants.TYPE, user.getTokenType());

      return namedJdbcTemplate.queryForObject(emailSql, paramMap, new RowMapper<UserDTO>() {
        @Override
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.RECORD_ID));
          user.setUserName(rs.getString(AppConstants.USER_NAME));
          user.setUserContact(rs.getString(AppConstants.CONTACT_NUMBER));
          user.setEmail(rs.getString(AppConstants.EMAIL));
          user.setActivationStatus(rs.getString(AppConstants.ACTIVATION_STATUS));
          user.setUserType(rs.getString(AppConstants.UUID));
          return user;
        }
      });
    } catch (EmptyResultDataAccessException e) {
      logger.error("no record found . Explicitly throwing exception", e);
      return null;
    } finally {
      logger.debug("Exiting ProfessionalDao << getTokenCheckforSms() method");
    }
  }

  @Override
  public List<OspContactBean> fetchContactDetails(long profId) {

    List<OspContactBean> contactDetailList = null;
    {

      String getAddressSql =
          "select oc.CONTACT_ID,oc.CONTACT_TYPE,oc.CONTACT_PHONE,oc.CONTACT_EMAIL,oc.ACTIVE_STATUS from OSP_PROFESSIONAL op, OSP_CONTACT oc,OSP_PROF_CONTACT_MAP ocm  where ocm.CONTACT_ID = oc.CONTACT_ID and op.PROF_ID = ocm.PROF_ID"
              + " and op.PROF_ID =:" + AppConstants.PROF_ID;

      HashMap<String, Long> paramMap = new HashMap<String, Long>();
      paramMap.put(AppConstants.PROF_ID, profId);

      contactDetailList =
          namedJdbcTemplate.query(getAddressSql, paramMap, new RowMapper<OspContactBean>() {

            @Override
            public OspContactBean mapRow(ResultSet rs, int rowNum) throws SQLException {

              OspContactBean con = new OspContactBean();
              con.setContactId(rs.getInt(1));
              con.setContactType(rs.getInt(2));
              con.setContactPhone(rs.getString(3));
              con.setContactEmail(rs.getString(4));
              con.setActiveStatus(rs.getInt(5));
              return con;

            }
          });

    }
    return contactDetailList;
  }

  @Override
  public List<OspAddressBean> fetchAddressDetails(long profId) {

    List<OspAddressBean> addressDetailList = null;
    {

      String getAddressSql =
          "select oad.ADDRESS_ID,oad.LOCATION_ID,oad.OTHER_AREA,oad.LINE_1,oad.LINE_2,oad.PIN_CODE,oad.ACTIVE_STATUS from OSP_PROFESSIONAL op,OSP_ADDRESS oad,OSP_PROF_ADDRESS_MAP oadmap  where oadmap.ADDRESS_ID = oad.ADDRESS_ID and op.PROF_ID = oadmap.PROF_ID"
              + " and op.PROF_ID =:" + AppConstants.PROF_ID;

      HashMap<String, Long> paramMap = new HashMap<String, Long>();
      paramMap.put(AppConstants.PROF_ID, profId);

      addressDetailList =
          namedJdbcTemplate.query(getAddressSql, paramMap, new RowMapper<OspAddressBean>() {

            @Override
            public OspAddressBean mapRow(ResultSet rs, int rowNum) throws SQLException {
              OspAddressBean address = new OspAddressBean();
              address.setAddressId(rs.getInt(1));
              address.setLocationId(rs.getInt(2));
              address.setOtherArea(rs.getString(3));
              address.setLine1(rs.getString(4));
              address.setLine2(rs.getString(5));
              address.setPinCode(rs.getString(6));
              address.setActiveStatus(rs.getInt(7));
              return address;
            }
          });

    }
    return addressDetailList;
  }

  @Override
  public List<OspProfessionalDTO> fetchApprovalProfList() throws OspDaoException {

    List<OspProfessionalDTO> professionalDetailList = null;
    try {

      int initalStatusCode =
          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_STATUS,
              AppConstants.PARAM_NAME_INITIAL).getParameterid();

      String getProSql
      /*
       * = "select * from OSP_PROFESSIONAL op, OSP_CONTACT oc, OSP_ADDRESS oad, " +
       * "OSP_PROF_SPECIALIZATIONS ops, OSP_PROF_ACADEMICS opa, OSP_PROF_EXPERIENCE ope, " +
       * "OSP_PROF_CONTACT_MAP ocm, OSP_PROF_ADDRESS_MAP oadmap" + " where op.PROF_ID = ocm.PROF_ID"
       * + " and ocm.CONTACT_ID = oc.CONTACT_ID" + " and op.PROF_ID = oadmap.PROF_ID" +
       * " and oadmap.ADDRESS_ID = oad.ADDRESS_ID" + " and op.PROF_ID = ops.PROF_ID" +
       * " and op.PROF_ID = opa.PROF_ID" + " and op.PROF_ID = ope.PROF_ID and op.STATUS=:STATUS";
       */

      = "select * from OSP_PROFESSIONAL op where op.STATUS=:STATUS";

      HashMap<String, Integer> paramMap = new HashMap<String, Integer>();
      paramMap.put(AppConstants.USER_STATUS, initalStatusCode);

      professionalDetailList =
          namedJdbcTemplate.query(getProSql, paramMap, new RowMapper<OspProfessionalDTO>() {
            @Override
            public OspProfessionalDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
              OspProfessionalDTO prof = new OspProfessionalDTO();

              prof.setProfId(rs.getInt(1));
              prof.setRecordId(rs.getInt(2));
              prof.setProfFirstName(rs.getString(3));
              prof.setProfMiddleName(rs.getString(4));
              prof.setProfLastName(rs.getString(5));
              prof.setProfEmpId(rs.getString(6));
              prof.setProfDob(rs.getDate(7));
              prof.setProfGender(rs.getInt(8));
              prof.setProfNationality(rs.getString(9));
              prof.setProfPan(rs.getString(10));
              prof.setProfMeritalStatus(rs.getInt(11));
              prof.setProfMerriageAnniversary(rs.getDate(12));
              prof.setDndActivatedFlag(rs.getInt(13));
              prof.setProfSignature(rs.getBytes(14));
              prof.setProfPhotograph(rs.getString(15));
              prof.setProfSubscId(rs.getString(16));
              prof.setProfPublicId(rs.getString(17));
              prof.setProfFees(rs.getDouble(18));
              prof.setProfRemark(rs.getString(19));
              prof.setStatus(rs.getInt(20));
              prof.setCreatedBy(rs.getString(21));
              prof.setCreatedTs(rs.getTimestamp(22));

              prof.setAddressList(fetchAddressDetails(prof.getProfId()));
              prof.setContactList(fetchContactDetails(prof.getProfId()));

              return prof;
            }
          });

    } catch (DataAccessException exp) {
      throw new OspDaoException(exp);

    }
    return professionalDetailList;
  }

}
