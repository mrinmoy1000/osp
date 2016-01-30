package com.flamingos.osp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.util.AppConstants;

@Repository
public class SignUpDaoImpl implements SignUpDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private ConfigParamBean configParamBean;

  private String getUserSql = "SELECT " + AppConstants.RECORD_ID + "," + AppConstants.USER_NAME
      + "," + AppConstants.PASSWORD + "," + AppConstants.CONTACT_NUMBER + "," + AppConstants.EMAIL
      + "," + AppConstants.ACTIVATION_STATUS + " FROM OSP_USER_CREDENTIAL WHERE ";

  @Override
  public UserDTO findByUserName(String userName) throws OspDaoException {
    String userNameSql = getUserSql + AppConstants.USER_NAME + "=:username";
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("username", userName);

    try {
      return namedParameterJdbcTemplate.queryForObject(userNameSql, paramMap,
          new RowMapper<UserDTO>() {
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
      return null;

    }


  }

  @Override
  public UserDTO findByContact(Long contact) throws OSPBusinessException {

    String contactSql = getUserSql + AppConstants.CONTACT_NUMBER + "=:contact";
    Map<String, Long> paramMap = new HashMap<String, Long>();
    paramMap.put("contact", contact);

    try {
      return namedParameterJdbcTemplate.queryForObject(contactSql, paramMap,
          new RowMapper<UserDTO>() {
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
      return null;

    }


  }

  @Override
  public UserDTO findByEmailAddress(String email) throws OSPBusinessException {

    String emailSql = getUserSql + AppConstants.EMAIL + "=:email";
    Map<String, String> paramMap = new HashMap<String, String>();
    paramMap.put("email", email);
    try {
      return namedParameterJdbcTemplate.queryForObject(emailSql, paramMap,
          new RowMapper<UserDTO>() {
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
      return null;

    }

  }

  @Override
  public void createNewUser(UserBean user, int emailExpireTime, int smsExpireTime)
      throws OspDaoException {

    String sql =
        "INSERT INTO OSP_USER_CREDENTIAL"
            + "      (USER_NAME,RECORD_ID,ROLE_ID,RECORD_TYPE,USER_CRED,USER_FIRST_NAME,USER_MIDDLE_NAME,USER_LAST_NAME,USER_REG_EMAIL,USER_REG_PHONE,CRED_EXPIRY_DATE,NO_OF_ATTEMPTS,EMAIL_VERYFIED,SMS_VERIFIED,ACTIVATION_STATUS,LAST_LOGIN_TS,CREATED_BY,CREATED_TS,UPDATED_BY,UPDATED_TS) "
            + "VALUES(:USER_NAME,:RECORD_ID,:ROLE_ID, :RECORD_TYPE, :USER_CRED, :USER_FIRST_NAME, :USER_MIDDLE_NAME, :USER_LAST_NAME,:USER_REG_EMAIL,:USER_REG_PHONE, :CRED_EXPIRY_DATE, :NO_OF_ATTEMPTS, :EMAIL_VERYFIED, :SMS_VERIFIED, :ACTIVATION_STATUS,  :LAST_LOGIN_TS, :CREATED_BY, :CREATED_TS, :UPDATED_BY, :UPDATED_TS )";

    MapSqlParameterSource userDetailsMap = new MapSqlParameterSource();
    userDetailsMap.addValue(AppConstants.USER_NAME, user.getUserName());
    userDetailsMap.addValue(AppConstants.RECORD_ID, user.getId());
    userDetailsMap.addValue(AppConstants.ROLE_ID, user.getRoleId());
    userDetailsMap.addValue(AppConstants.RECORD_TYPE, user.getRecordType());
    userDetailsMap.addValue(AppConstants.PASSWORD, user.getPassword());
    userDetailsMap.addValue(AppConstants.FIRST_NAME, user.getFirstName());
    userDetailsMap.addValue(AppConstants.MIDDLE_NAME, user.getMiddleName());
    userDetailsMap.addValue(AppConstants.LAST_NAME, user.getLastName());
    userDetailsMap.addValue(AppConstants.EMAIL, user.getEmail());
    userDetailsMap.addValue(AppConstants.CONTACT_NUMBER, user.getContactNumber());
    userDetailsMap.addValue(AppConstants.CARD_EXPIRY_DATE, new Timestamp(new Date().getTime()));
    userDetailsMap.addValue(AppConstants.NO_OF_ATTEMPTS, 0);
    userDetailsMap.addValue(AppConstants.EMAIL_VERIFIED, 0);
    userDetailsMap.addValue(AppConstants.SMS_VERIFIED, 0);
    userDetailsMap.addValue(AppConstants.ACTIVATION_STATUS, 0);
    userDetailsMap.addValue(AppConstants.LOGIN_TS, new Timestamp(new Date().getTime()));
    userDetailsMap.addValue(AppConstants.CREATED_BY, user.getUserName());
    userDetailsMap.addValue(AppConstants.CREATED_TS, new Timestamp(new Date().getTime()));
    userDetailsMap.addValue(AppConstants.UPDATE_BY, null);
    userDetailsMap.addValue(AppConstants.UPDATE_TS, null);

    KeyHolder key = new GeneratedKeyHolder();
    namedParameterJdbcTemplate.update(sql, userDetailsMap, key);
    long getInsertedUser = (Long) key.getKey();

    String insertAccessToken =
        "INSERT INTO OSP_ACCESS_TOKEN VALUES " + "(:" + AppConstants.USER_ID + "," + ":"
            + AppConstants.TYPE + "," + ":" + AppConstants.UUID + "," + ":"
            + AppConstants.TOKEN_EXPIRY_DT + "," + ":" + AppConstants.IS_USED + "," + ":"
            + AppConstants.CREATED_TS + "," + ":" + AppConstants.UPDATED_TS + "," + ":"
            + AppConstants.CREATED_BY + "," + ":" + AppConstants.UPDATED_BY + ")";

    ConfigParamDto oParamEmailChannel =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
            AppConstants.PARAM_NAME_EMAIL);
    Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
    accessTokenMapforEmail.put(AppConstants.USER_ID, getInsertedUser);
    accessTokenMapforEmail.put(AppConstants.TYPE, oParamEmailChannel.getParameterid());
    accessTokenMapforEmail.put(AppConstants.UUID, user.getEmailUUID());
    accessTokenMapforEmail.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime()
        + (1 * emailExpireTime * 60 * 60 * 1000)));
    accessTokenMapforEmail.put(AppConstants.IS_USED, 0);
    accessTokenMapforEmail.put(AppConstants.CREATED_TS, new Timestamp(new Date().getTime()));
    accessTokenMapforEmail.put(AppConstants.UPDATE_TS, null);
    accessTokenMapforEmail.put(AppConstants.CREATED_BY, user.getUserName());
    accessTokenMapforEmail.put(AppConstants.UPDATE_BY, null);
    namedParameterJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
    ConfigParamDto oParamSMSChannel =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
            AppConstants.PARAM_NAME_SMS);
    Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
    accessTokenMapforSms.put(AppConstants.USER_ID, getInsertedUser);
    accessTokenMapforSms.put(AppConstants.TYPE, oParamSMSChannel.getParameterid());
    accessTokenMapforSms.put(AppConstants.UUID, user.getSmsUUID());
    accessTokenMapforSms.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime()
        + (1 * smsExpireTime * 60 * 60 * 1000)));
    accessTokenMapforSms.put(AppConstants.IS_USED, 0);
    accessTokenMapforSms.put(AppConstants.CREATED_TS, new Timestamp(new Date().getTime()));
    accessTokenMapforSms.put(AppConstants.UPDATE_TS, null);
    accessTokenMapforSms.put(AppConstants.CREATED_BY, user.getUserName());
    accessTokenMapforSms.put(AppConstants.UPDATE_BY, null);
    namedParameterJdbcTemplate.update(insertAccessToken, accessTokenMapforSms);
  }

  @Override
  public String updateUser(UserBean user) throws OspDaoException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public UserDTO checkForProfessional(UserBean user) throws OspDaoException {
    String profSql = "SELECT * from OSP_PROFESSIONAL  where " + AppConstants.PROF_ID + "=:PROF_ID";
    Map<String, Long> paramMap = new HashMap<String, Long>();
    paramMap.put("PROF_ID", user.getProf_id());

    try {

      return namedParameterJdbcTemplate.queryForObject(profSql, paramMap, new RowMapper<UserDTO>() {
        public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
          UserDTO user = new UserDTO();
          user.setUserId(rs.getLong(AppConstants.PROF_ID));
          return user;
        }
      });

    } catch (RuntimeException e) {
      throw new OspDaoException(e);
    }
  }

  @Override
  public void mapUserAndProfessional(long recordId, long profId) throws OspDaoException {
    try {
      String updateEmailStatusSql =
          " UPDATE OSP_PROFESSIONAL prof  " + "  SET RECORD_ID = ? where prof."
              + AppConstants.PROF_ID + " = ?";
      int count = jdbcTemplate.update(updateEmailStatusSql, new Object[] {recordId, profId});
      if (count != 1) {
        throw new OspDaoException();
      }
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
  }
}
