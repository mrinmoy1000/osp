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

}
