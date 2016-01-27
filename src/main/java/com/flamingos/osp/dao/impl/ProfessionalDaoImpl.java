package com.flamingos.osp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
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

  @Transactional
  @Override
  public int addProfile(OspProfessionalBean professionalBean) throws OspDaoException {
    try {

      Map<String, Object> professionalDetailsMap = new HashMap<String, Object>();
      professionalDetailsMap.put("RECORD_ID", professionalBean.getRecordId());
      professionalDetailsMap.put("PROF_FIRST_NAME", professionalBean.getProfFirstName());
      professionalDetailsMap.put("PROF_MIDDLE_NAME", professionalBean.getProfMiddleName());
      professionalDetailsMap.put("PROF_LAST_NAME", professionalBean.getProfLastName());
      professionalDetailsMap.put("PROF_EMP_ID", professionalBean.getProfEmpId());
      professionalDetailsMap.put("PROF_DOB", professionalBean.getProfDob());
      professionalDetailsMap.put("PROF_GENDER", professionalBean.getProfGender());
      professionalDetailsMap.put("PROF_NATIONALITY", professionalBean.getProfNationality());
      professionalDetailsMap.put("PROF_PAN", professionalBean.getProfPan());
      professionalDetailsMap.put("PROF_MERITGAL_STATUS", professionalBean.getProfMeritalStatus());
      professionalDetailsMap.put("PROF_MERRIAGE_ANNIVERSARY",
          professionalBean.getProfMerriageAnniversary());
      professionalDetailsMap.put("DND_ACTIVATED_FLAG", professionalBean.getDndActivatedFlag());
      professionalDetailsMap.put("PROF_SIGNATURE", professionalBean.getProfSignature());
      professionalDetailsMap.put("PROF_SUBSC_ID", professionalBean.getProfSubscId());
      professionalDetailsMap.put("PROF_PUBLIC_ID", professionalBean.getProfPublicId());
      professionalDetailsMap.put("PROF_FEES", professionalBean.getProfFees());
      professionalDetailsMap.put("PROF_REMARK", professionalBean.getProfRemark());
      professionalDetailsMap.put("STATUS", professionalBean.getStatus());
      professionalDetailsMap.put("CREATED_TS", new Timestamp(new Date().getTime()));
      professionalDetailsMap.put("CREATED_BY", professionalBean.getCreatedBy());
      SimpleJdbcInsert simpleInsert =
          new SimpleJdbcInsert(jdbcTemplate).withTableName("OSP_PROFESSIONAL")
              .usingGeneratedKeyColumns("PROF_ID");
      Number generateProfKey = simpleInsert.executeAndReturnKey(professionalDetailsMap);


      Map<String, Object> addressMap = new HashMap<String, Object>();
      addressMap.put("LOCATION_ID", professionalBean.getAddress().getLocationId());
      addressMap.put("OTHER_AREA", professionalBean.getAddress().getOtherArea());
      addressMap.put("LINE_1", professionalBean.getAddress().getLine1());
      addressMap.put("LINE_2", professionalBean.getAddress().getLine2());
      addressMap.put("ACTIVE_STATUS", professionalBean.getAddress().getActiveStatus());
      addressMap.put("CREATED_TS", new Timestamp(new Date().getTime()));
      addressMap.put("CREATED_BY", professionalBean.getAddress().getCreatedBy());
      simpleInsert =
          new SimpleJdbcInsert(jdbcTemplate).withTableName("OSP_ADDRESS").usingGeneratedKeyColumns(
              "ADDRESS_ID");
      Number generateAddressId = simpleInsert.executeAndReturnKey(addressMap);


      Map<String, Object> contactMap = new HashMap<String, Object>();
      contactMap.put("CONTACT_TYPE", professionalBean.getContact().getContactType());
      contactMap.put("CONTACT_PHONE", professionalBean.getContact().getContactPhone());
      contactMap.put("CONTACT_EMAIL", professionalBean.getContact().getContactEmail());
      contactMap.put("ACTIVE_STATUS", professionalBean.getContact().getActiveStatus());
      contactMap.put("CREATED_TS", new Timestamp(new Date().getTime()));
      contactMap.put("CREATED_BY", professionalBean.getContact().getCreatedBy());
      simpleInsert =
          new SimpleJdbcInsert(jdbcTemplate).withTableName("OSP_CONTACT").usingGeneratedKeyColumns(
              "CONTACT_ID");
      Number generateContactId = simpleInsert.executeAndReturnKey(contactMap);


      professionalBean.setProfId((Integer) generateProfKey);
      professionalBean.getAddress().setAddressId((Integer) generateAddressId);
      professionalBean.getContact().setContactId((Integer) generateContactId);


      String insertOspProfAddressMap =
          "INSERT INTO OSP_PROF_ACCESS_MAP VALUES (:PROF_ID," + ":ADDRESS_ID," + ":ACTIVE_STATUS)";

      Map<String, Object> OspProfAddrssMap = new HashMap<String, Object>();
      OspProfAddrssMap.put("PROF_ID", professionalBean.getProfId());
      OspProfAddrssMap.put("ADDRESS_ID", professionalBean.getAddress().getAddressId());
      OspProfAddrssMap.put("ACTIVE_STATUS", 1);

      namedJdbcTemplate.update(insertOspProfAddressMap, OspProfAddrssMap);

      String insertOspProfContactMap =
          "INSERT INTO OSP_PROF_ACCESS_MAP VALUES (:PROF_ID," + ":CONTACT_ID," + ":ACTIVE_STATUS)";

      Map<String, Object> OspProfContactMap = new HashMap<String, Object>();
      OspProfContactMap.put(AppConstants.PROF_ID, professionalBean.getProfId());
      OspProfContactMap.put("CONTACT_ID", professionalBean.getAddress().getAddressId());
      OspProfContactMap.put("ACTIVE_STATUS", 1);

      namedJdbcTemplate.update(insertOspProfContactMap, OspProfContactMap);


      return 0;
    } catch (RuntimeException exp) {
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
    } catch (RuntimeException exp) {
      throw new OspDaoException();
    }


  }

}
