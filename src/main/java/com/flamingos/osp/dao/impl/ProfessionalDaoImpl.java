package com.flamingos.osp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.OspAddressBean;
import com.flamingos.osp.bean.OspContactBean;
import com.flamingos.osp.bean.OspExperienceBean;
import com.flamingos.osp.bean.OspProfAcademicsBean;
import com.flamingos.osp.bean.OspProfSpecializationBean;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ProfessionalDaoImpl implements ProfessionalDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public void emailUpdateStatus(UserBean user, AccessToken access) throws OspDaoException {
    try {
      String updateEmailStatusSql =
          " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up " + "  SET up."
              + AppConstants.ACTIVATION_STATUS + "= ? ," + " up." + AppConstants.EMAIL_VERIFIED
              + " = ? " + " , up." + AppConstants.LOGIN_TS + " = ? " + " , acc."
              + AppConstants.TOKEN_EXPIRY_DT + " = ? " + " where up." + AppConstants.USER_NAME
              + " = ? " + " and acc." + AppConstants.UUID + " = ? " + " and  up."
              + AppConstants.RECORD_ID + " = acc." + AppConstants.RECORD_ID;
      int count =
          jdbcTemplate.update(
              updateEmailStatusSql,
              new Object[] {user.getActiveStatus(), user.getEmailVerified(),
                  new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()),
                  user.getUserName(), user.getEmailUUID()

              });
      if (count < 1) {
        throw new OspDaoException();
      }
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }

  @Override
  public void smsUpdateStatus(UserBean user, AccessToken access) throws OspDaoException {
    try {
      String updateSmsStatusSql =
          " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up " + "  SET up."
              + AppConstants.ACTIVATION_STATUS + "= ? ," + "   up." + AppConstants.SMS_VERIFIED
              + " = ? , " + " up." + AppConstants.LOGIN_TS + " = ?, " + " acc."
              + AppConstants.TOKEN_EXPIRY_DT + " =  ?" + "  where up." + AppConstants.USER_NAME
              + " = ?" + " and acc." + AppConstants.UUID + " = ? "

              + " and  up." + AppConstants.RECORD_ID + " = acc." + AppConstants.RECORD_ID;
      int count =
          jdbcTemplate.update(
              updateSmsStatusSql,
              new Object[] {user.getActiveStatus(), user.getSmsVerfied(),
                  new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()),
                  user.getUserName(), user.getSmsUUID()

              });
      if (count < 1) {
        throw new OspDaoException();
      }
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }

  @Override
  public void FUPUpdateStatus(UserBean user, AccessToken access) throws OspDaoException {
    try {
      String updateEmailStatusSql =
          " UPDATE OSP_ACCESS_TOKEN acc , osp_user_password up " + "  SET up."
              + AppConstants.ACTIVATION_STATUS + " = ? ," + "  where up." + AppConstants.USER_NAME
              + " = ? " + "  and acc." + AppConstants.UUID + " = ? " + " up."
              + AppConstants.LOGIN_TS + " = ? " + " acc." + AppConstants.TOKEN_EXPIRY_DT + "= ?"
              + "and  up." + AppConstants.RECORD_ID + " = acc." + AppConstants.USER_ID;
      int count =
          jdbcTemplate.update(updateEmailStatusSql,
              new Object[] {access.getActiveIndicator(), user.getUserName(), user.getFupUUID(),
                  new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime())});
      if (count != 1) {
        throw new OspDaoException();
      }
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }

  @Override
  public UserDTO getUserLinkValidCheckForEmail(UserBean user, AccessToken access)
      throws OspDaoException {

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

    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }
  }

  @Override
  public UserDTO getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
      throws OspDaoException {
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

    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }
  }

  @Override
  public UserDTO getUserLinkValidCheckForSms(UserBean user, AccessToken access)
      throws OspDaoException {
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

    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }
  }


  @Override
  public List<UserBean> getTokenCheck(UserBean user, AccessToken access) throws OspDaoException {
    List<UserBean> userTokenList = null;
    try {

      String getUserSql =
          "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN acc , osp_parameter param"
              + " where acc." + AppConstants.USER_ID + " = up." + AppConstants.RECORD_ID
              + " and param." + AppConstants.PARAM_ID + " = acc." + AppConstants.TYPE + "and  up."
              + AppConstants.USER_NAME + "= ? and " + " acc." + AppConstants.TOKEN_EXPIRY_DT
              + " > ?";
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("username", user.getUserName());
      paramMap.put("EXPIRY_DT", new Date().getTime());
      userTokenList = namedJdbcTemplate.query(getUserSql, paramMap, new RowMapper<UserBean>() {
        public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserBean user = new UserBean();
          user.setTokenType(rs.getString(AppConstants.PARAM_VALUE));
          user.setEmailVerified(rs.getInt(AppConstants.EMAIL_VERIFIED));
          user.setSmsVerfied(rs.getInt(AppConstants.SMS_VERIFIED));
          return user;
        }
      });

    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);

    }
    return userTokenList;
  }

  @Override
  public int generateNewEmailToken(UserBean user, int emailExpireTime) throws OspDaoException {
    try {
      String getUserSql = "select record_id from OSP_USER_CREDENTIAL  where user_name = ?";
      int getInsertedUser =
          jdbcTemplate.queryForObject(getUserSql, new Object[] {user.getUserName()}, Integer.class);
      String insertAccessToken =
          "INSERT INTO osp_access_token VALUES " + "(:" + AppConstants.USER_ID + "," + ":"
              + AppConstants.TYPE + "," + ":" + AppConstants.UUID + "," + ":"
              + AppConstants.TOKEN_EXPIRY_DT + "," + ":" + AppConstants.IS_USED + "," + ":"
              + AppConstants.CREATED_BY + "," + ":" + AppConstants.CREATED_TS + "," + ":"
              + AppConstants.UPDATE_BY + "," + ":" + AppConstants.UPDATE_TS + ");";
      Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
      accessTokenMapforEmail.put(AppConstants.USER_ID, getInsertedUser);
      accessTokenMapforEmail.put(AppConstants.TYPE, 22);
      accessTokenMapforEmail.put(AppConstants.UUID, user.getEmailUUID());
      accessTokenMapforEmail.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime()
          + (1 * (emailExpireTime) * 60 * 60 * 1000)));
      accessTokenMapforEmail.put(AppConstants.IS_USED, 0);
      accessTokenMapforEmail.put(AppConstants.CREATED_BY, user.getUserName());
      namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
      return 0;
    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }
  }

  @Override
  public int generateNewSmsToken(UserBean user, int smsExpireTime) throws OspDaoException {
    try {
      String getUserSql = "select record_id from OSP_USER_CREDENTIAL  where user_name = ?";
      int getInsertedUser =
          jdbcTemplate.queryForObject(getUserSql, new Object[] {user.getUserName()}, Integer.class);
      String insertAccessToken =
          "INSERT INTO osp_access_token VALUES " + "(:" + AppConstants.USER_ID + "," + ":"
              + AppConstants.TYPE + "," + ":" + AppConstants.UUID + "," + ":"
              + AppConstants.TOKEN_EXPIRY_DT + "," + ":" + AppConstants.IS_USED + "," + ":"
              + AppConstants.CREATED_BY + "," + ":" + AppConstants.CREATED_TS + "," + ":"
              + AppConstants.UPDATE_BY + "," + ":" + AppConstants.UPDATE_TS + ");";
      Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
      accessTokenMapforSms.put(AppConstants.USER_ID, getInsertedUser);
      accessTokenMapforSms.put(AppConstants.TYPE, 21);
      accessTokenMapforSms.put(AppConstants.UUID, user.getSmsUUID());
      accessTokenMapforSms.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime()
          + (1 * smsExpireTime * 60 * 60 * 1000)));
      accessTokenMapforSms.put(AppConstants.IS_USED, 0);
      accessTokenMapforSms.put(AppConstants.CREATED_BY, user.getUserName());
      namedJdbcTemplate.update(insertAccessToken, accessTokenMapforSms);
      return 1;
    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }

  }

  @Override
  public UserDTO checkForForgotPassword(UserBean user, AccessToken access) throws OspDaoException {
    try {
      String emailSql =
          "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN " + " acc  where" + " up."
              + AppConstants.USER_NAME + "= :user_name and " + " acc." + AppConstants.UUID
              + "=:UUID and" + " and  acc." + AppConstants.TOKEN_EXPIRY_DT + "> :EXPIRY_DT"
              + " and  acc." + AppConstants.RECORD_ID + "= up." + AppConstants.RECORD_ID;
      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("user_name", user.getUserName());
      paramMap.put("UUID", user.getFupUUID());
      paramMap.put("EXPIRY_DT", access.getExpireTime());

      return namedJdbcTemplate.queryForObject(emailSql, paramMap, new RowMapper<UserDTO>() {
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.RECORD_ID));
          return user;
        }
      });

    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }

  }

  @Override
  public void updatePassword(UserBean user) throws OspDaoException {
    try {
      String updatePassword =
          " UPDATE  OSP_USER_CREDENTIAL up " + "  SET up." + AppConstants.PASSWORD + " = ?  "
              + " up." + AppConstants.LOGIN_TS + " = ? " + "where  up." + AppConstants.RECORD_ID
              + " =  ? ";
      int count =
          jdbcTemplate.update(updatePassword, new Object[] {user.getPassword(), user.getUser_id()});
      if (count != 1) {
        throw new OspDaoException();
      }
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }
  
  @Override
  public int addProfile(OspProfessionalBean professionalBean) throws OspDaoException {
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

      professionalBean.setProfId(generatedKeyHolder.getKey().intValue());
      return professionalBean.getProfId();
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }
  }

  @Override
  public void approveProfile(OspProfessionalBean professionalBean, int param_id)
      throws OspDaoException {
    try {
      String updateStatus =
          " UPDATE  osp_professional opp" + "  SET opp." + AppConstants.USER_STATUS + "= ? ,"
              + "where " + AppConstants.PROF_ID + " = ?";

      int count =
          jdbcTemplate.update(updateStatus, new Object[] {param_id, professionalBean.getProfId()});
      if (count != 1) {
        throw new OspDaoException();
      }
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }


  }
  
  @Override
  public List<OspProfessionalBean> getAllProfessionalDetails() throws OspDaoException {

    List<OspProfessionalBean> professionalDetailList = null;
    try {

      String getProSql =
          "select * from OSP_PROFESSIONAL op, OSP_CONTACT oc, OSP_ADDRESS oad, "
              + "OSP_PROF_SPECIALIZATIONS ops, OSP_PROF_ACADEMICS opa, OSP_PROF_EXPERIENCE ope, "
              + "OSP_PROF_CONTACT_MAP ocm, OSP_PROF_ADDRESS_MAP oadmap"
              + " where op.PROF_ID = ocm.PROF_ID" + " and ocm.CONTACT_ID = oc.CONTACT_ID"
              + " and op.PROF_ID = oadmap.PROF_ID" + " and oadmap.ADDRESS_ID = oad.ADDRESS_ID"
              + " and op.PROF_ID = ops.PROF_ID" + " and op.PROF_ID = opa.PROF_ID"
              + " and op.PROF_ID = ope.PROF_ID";


      professionalDetailList =
          namedJdbcTemplate.query(getProSql, new RowMapper<OspProfessionalBean>() {
            @Override
            public OspProfessionalBean mapRow(ResultSet rs, int rowNum) throws SQLException {
              OspProfessionalBean prof = new OspProfessionalBean();
              OspAddressBean address = new OspAddressBean();
              OspContactBean con = new OspContactBean();

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
              prof.setProfSignature(rs.getString(14));
              prof.setProfPhotograph(rs.getString(15));
              prof.setProfSubscId(rs.getString(16));
              prof.setProfPublicId(rs.getString(17));
              prof.setProfFees(rs.getDouble(18));
              prof.setProfRemark(rs.getString(19));
              prof.setStatus(rs.getInt(20));
              prof.setCreatedBy(rs.getString(21));
              prof.setCreatedTs(rs.getTimestamp(22));

              con.setContactId(rs.getInt(25));
              con.setContactType(rs.getInt(26));
              con.setContactPhone(rs.getString(27));
              con.setContactEmail(rs.getString(28));
              con.setActiveStatus(rs.getInt(29));
              con.setCreatedTs(rs.getTimestamp(30));
              con.setCreatedBy(rs.getString(32));
              prof.setContact(con);

              address.setAddressId(rs.getInt(34));
              address.setLocationId(rs.getInt(35));
              address.setOtherArea(rs.getString(36));
              address.setLine1(rs.getString(37));
              address.setLine2(rs.getString(38));
              address.setPinCode(rs.getString(39));
              address.setActiveStatus(rs.getInt(40));
              address.setCreatedTs(rs.getTimestamp(41));
              address.setCreatedBy(rs.getString(43));
              prof.setAddress(address);


              return prof;
            }
          });

    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);

    }
    return professionalDetailList;
  }

  @Override
  public OspProfessionalBean getProfessionalDetails(int profId) throws OspDaoException {

    OspProfessionalBean professionalDetail = null;

    List<OspProfSpecializationBean> specializationList = null;
    List<OspProfAcademicsBean> qualificationList = null;
    List<OspExperienceBean> experienceList = null;

    try {

      String getSepecialzationSql =
          "select * from OSP_PROFESSIONAL op, OSP_PROF_SPECIALIZATIONS ops"
              + " where op.PROF_ID = ops.PROF_ID" + " and op.PROF_ID = :prof_id ";

      String getAcademicsSql =
          "select * from OSP_PROFESSIONAL op, OSP_PROF_ACADEMICS opa"
              + " where op.PROF_ID = opa.PROF_ID" + " and op.PROF_ID = :prof_id ";

      String getExperienceSql =
          "select * from OSP_PROFESSIONAL op, OSP_PROF_EXPERIENCE ope"
              + " where op.PROF_ID = ope.PROF_ID" + " and op.PROF_ID = :prof_id ";

      Map<String, Object> paramMap = new HashMap<String, Object>();
      paramMap.put("prof_id", profId);



      specializationList =
          namedJdbcTemplate.queryForList(getSepecialzationSql, paramMap,
              OspProfSpecializationBean.class);
      qualificationList =
          namedJdbcTemplate.queryForList(getAcademicsSql, paramMap, OspProfAcademicsBean.class);
      experienceList =
          namedJdbcTemplate.queryForList(getExperienceSql, paramMap, OspExperienceBean.class);



      String getProSql =
          "select * from OSP_PROFESSIONAL op, OSP_CONTACT oc, OSP_ADDRESS oad,"
              + " OSP_PROF_CONTACT_MAP ocm, OSP_PROF_ADDRESS_MAP oadmap"
              + " where op.PROF_ID = ocm.PROF_ID" + " and ocm.CONTACT_ID = oc.CONTACT_ID"
              + " and op.PROF_ID = oadmap.PROF_ID" + " and oadmap.ADDRESS_ID = oad.ADDRESS_ID"
              + " and op.PROF_ID = :prof_id ";



      professionalDetail =
          namedJdbcTemplate.queryForObject(getProSql, paramMap,
              new RowMapper<OspProfessionalBean>() {
                @Override
                public OspProfessionalBean mapRow(ResultSet rs, int rowNum) throws SQLException {
                  OspProfessionalBean prof = new OspProfessionalBean();
                  OspAddressBean address = new OspAddressBean();
                  OspContactBean con = new OspContactBean();

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
                  prof.setProfSignature(rs.getString(14));
                  prof.setProfPhotograph(rs.getString(15));
                  prof.setProfSubscId(rs.getString(16));
                  prof.setProfPublicId(rs.getString(17));
                  prof.setProfFees(rs.getDouble(18));
                  prof.setProfRemark(rs.getString(19));
                  prof.setStatus(rs.getInt(20));
                  prof.setCreatedBy(rs.getString(21));
                  prof.setCreatedTs(rs.getTimestamp(22));

                  con.setContactId(rs.getInt(25));
                  con.setContactType(rs.getInt(26));
                  con.setContactPhone(rs.getString(27));
                  con.setContactEmail(rs.getString(28));
                  con.setActiveStatus(rs.getInt(29));
                  con.setCreatedTs(rs.getTimestamp(30));
                  con.setCreatedBy(rs.getString(32));
                  prof.setContact(con);

                  address.setAddressId(rs.getInt(34));
                  address.setLocationId(rs.getInt(35));
                  address.setOtherArea(rs.getString(36));
                  address.setLine1(rs.getString(37));
                  address.setLine2(rs.getString(38));
                  address.setPinCode(rs.getString(39));
                  address.setActiveStatus(rs.getInt(40));
                  address.setCreatedTs(rs.getTimestamp(41));
                  address.setCreatedBy(rs.getString(43));
                  prof.setAddress(address);


                  return prof;
                }
              });

      professionalDetail.setQualificationList(qualificationList);
      professionalDetail.setExperienceList(experienceList);
      professionalDetail.setSpecializationList(specializationList);

    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);

    }
    return professionalDetail;
  }


}
