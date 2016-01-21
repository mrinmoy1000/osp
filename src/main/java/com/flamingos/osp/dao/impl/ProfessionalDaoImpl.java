package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPSignupConstant;
import com.flamingos.osp.dao.ProfessionalDao;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.config.JdbcNamespaceHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ProfessionalDaoImpl implements ProfessionalDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	@Override
	public void emailUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String updateEmailStatusSql = " UPDATE osp_access_token acc , osp_user_password up "
			+ "  SET up."+OSPSignupConstant.ACTIVATION_STATUS +"= ? ,"
				   +"up."+OSPSignupConstant.EMAIL_VERIFIED +" = ?  "
				   		+ "where up."+OSPSignupConstant.USER_NAME+" = ?"+
				   "and acc."+OSPSignupConstant.UUID+" = ?"+
				   		"and  up."+OSPSignupConstant.RECORD_ID +" = acc."+OSPSignupConstant.USER_ID;
			int count = jdbcTemplate.update(updateEmailStatusSql, new Object[] {
					access.getActiveIndicator(), user.getSmsVerfied(), user.getUserName(), user.getEmailUUID() });
			if (count != 1) {
				throw new OspDaoException();
			}
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}

	@Override
	public void smsUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String updateSmsStatusSql = " UPDATE osp_access_token acc , osp_user_password up "
					+ "  SET up."+OSPSignupConstant.ACTIVATION_STATUS +"= ? ,"
					   +"up."+OSPSignupConstant.SMS_VERIFIED +" = ?  "
					   		+ "where up."+OSPSignupConstant.USER_NAME+" = ?"+
					   "and acc."+OSPSignupConstant.UUID+" = ?"+
					   		"and  up."+OSPSignupConstant.RECORD_ID +" = acc."+OSPSignupConstant.USER_ID;
			int count = jdbcTemplate.update(
					updateSmsStatusSql,
					new Object[] { access.getActiveIndicator(),
							user.getEmailVerified(), user.getUserName(),
							user.getSmsUUID() });
			if (count != 1) {
				throw new OspDaoException();
			}
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}

	@Override
	public void FUPUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String updateEmailStatusSql = " UPDATE osp_access_token acc , osp_user_password up "
					+ "  SET up."+OSPSignupConstant.ACTIVATION_STATUS +"= ? ,"
					   		+ "where up."+OSPSignupConstant.USER_NAME+" = ?"+
					   "and acc."+OSPSignupConstant.UUID+" = ?"+
					   		"and  up."+OSPSignupConstant.RECORD_ID +" = acc."+OSPSignupConstant.USER_ID;
			int count = jdbcTemplate.update(
					updateEmailStatusSql,
					new Object[] { access.getActiveIndicator(),
						 user.getUserName(),user.getFupUUID() });
			if (count != 1) {
				throw new OspDaoException();
			}
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}

	@Override
	public UserDTO getUserLinkValidCheckForEmail(UserBean user, AccessToken access)
			throws OspDaoException {

		try {
			String emailSql = "select * from osp_user_password up , osp_access_token "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " up."+OSPSignupConstant.ACTIVATION_STATUS+"=:activation_status and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.EXPIRY_DT+ "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.USER_ID+"= up."+OSPSignupConstant.RECORD_ID; 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_name", user.getUserName());
			paramMap.put("UUID", user.getEmailUUID());
			paramMap.put("activation_status", access.getActiveIndicator());
			paramMap.put("TYPE", access.getType());
			paramMap.put("EXPIRY_DT", access.getExpireTime());
			
			return namedJdbcTemplate.queryForObject(emailSql, paramMap,
					new RowMapper<UserDTO>() {
				public UserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getLong(OSPSignupConstant.RECORD_ID));
					user.setUserName(rs.getString(OSPSignupConstant.USER_NAME));
					user.setUserPass(rs.getString(OSPSignupConstant.PASSWORD));
					user.setUserContact(rs.getString(OSPSignupConstant.CONTACT_NUMBER));
					user.setEmail(rs.getString(OSPSignupConstant.EMAIL));
					user.setActivationStatus(rs
							.getString(OSPSignupConstant.ACTIVATION_STATUS));

					return user;
				}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public UserDTO getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String emailSql = "select * from osp_user_password up , osp_access_token "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " up."+OSPSignupConstant.ACTIVATION_STATUS+"=:activation_status and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.EXPIRY_DT+ "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.USER_ID+"= up."+OSPSignupConstant.RECORD_ID;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_name", user.getUserName());
			paramMap.put("UUID", user.getFupUUID());
			paramMap.put("activation_status", access.getActiveIndicator());
			paramMap.put("TYPE", access.getType());
			paramMap.put("EXPIRY_DT", access.getExpireTime());
			
			return namedJdbcTemplate.queryForObject(emailSql, paramMap,
					new RowMapper<UserDTO>() {
				public UserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getLong(OSPSignupConstant.RECORD_ID));
					user.setUserName(rs.getString(OSPSignupConstant.USER_NAME));
					user.setUserPass(rs.getString(OSPSignupConstant.PASSWORD));
					user.setUserContact(rs.getString(OSPSignupConstant.CONTACT_NUMBER));
					user.setEmail(rs.getString(OSPSignupConstant.EMAIL));
					user.setActivationStatus(rs
							.getString(OSPSignupConstant.ACTIVATION_STATUS));

					return user;
				}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public UserDTO getUserLinkValidCheckForSms(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String emailSql = "select count(1) from osp_user_password up , osp_access_token "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " up."+OSPSignupConstant.ACTIVATION_STATUS+"=:activation_status and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.EXPIRY_DT+ "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.USER_ID+"= up."+OSPSignupConstant.RECORD_ID;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_name", user.getUserName());
			paramMap.put("UUID", user.getEmailUUID());
			paramMap.put("activation_status", access.getActiveIndicator());
			paramMap.put("TYPE", access.getType());
			paramMap.put("EXPIRY_DT", access.getExpireTime());
			
			return namedJdbcTemplate.queryForObject(emailSql, paramMap,
					new RowMapper<UserDTO>() {
				public UserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getLong(OSPSignupConstant.RECORD_ID));
					user.setUserName(rs.getString(OSPSignupConstant.USER_NAME));
					user.setUserPass(rs.getString(OSPSignupConstant.PASSWORD));
					user.setUserContact(rs.getString(OSPSignupConstant.CONTACT_NUMBER));
					user.setEmail(rs.getString(OSPSignupConstant.EMAIL));
					user.setActivationStatus(rs
							.getString(OSPSignupConstant.ACTIVATION_STATUS));

					return user;
				}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	@Override
	public List<UserBean> getTokenCheck(UserBean user, AccessToken access)
			throws OspDaoException {
		List<UserBean> userTokenList = null;
		try {

			String getUserSql = "select * from osp_user_password up , osp_access_token acc , osp_parameter param"
					+ " where acc."+OSPSignupConstant.USER_ID+" = up."+OSPSignupConstant.RECORD_ID+
					 " and param."+OSPSignupConstant.PARAM_ID+" = acc."+OSPSignupConstant.TYPE 
					 + "and  up."+OSPSignupConstant.USER_NAME+"= ? and "+
					" acc."+OSPSignupConstant.EXPIRY_DT+" > ?";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", user.getUserName());
			paramMap.put("EXPIRY_DT", new Date().getTime());
			userTokenList = namedJdbcTemplate.query(getUserSql, paramMap,new RowMapper<UserBean>() {
				public UserBean mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserBean user = new UserBean();
					user.setTokenType(rs.getString(OSPSignupConstant.PARAM_VALUE));
					user.setEmailVerified(rs.getString(OSPSignupConstant.EMAIL_VERIFIED));
					user.setSmsVerfied(rs.getString(OSPSignupConstant.SMS_VERIFIED));
					return user;
				}
			});

		} catch (RuntimeException exp) {
			throw new OspDaoException();

		}
		return userTokenList;
	}

	@Override
	public int generateNewEmailToken(UserBean user, int emailExpireTime)
			throws OspDaoException {
		 String getUserSql = "select record_id from osp_user_password  where user_name = ?";
         int getInsertedUser = jdbcTemplate.queryForObject(getUserSql, new Object[]{user.getUserName()}, Integer.class);
		 String insertAccessToken = "INSERT INTO osp_access_token VALUES "
		 		+ "(:"+OSPSignupConstant.USER_ID+","
		 		+ ":"+OSPSignupConstant.TYPE+","
		 		+ ":"+OSPSignupConstant.UUID+","
		 		+ ":"+OSPSignupConstant.EXPIRY_DATE+","
		 		+ ":"+OSPSignupConstant.IS_USED+","
		 		+ ":"+OSPSignupConstant.CREATED_BY+","
		 		+ ":"+OSPSignupConstant.CREATED_TS+","
		 		+ ":"+OSPSignupConstant.UPDATE_BY+","
		 		+ ":"+OSPSignupConstant.UPDATE_TS+");";
         Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
         accessTokenMapforEmail.put(OSPSignupConstant.USER_ID, getInsertedUser);
         accessTokenMapforEmail.put(OSPSignupConstant.TYPE,22);
         accessTokenMapforEmail.put(OSPSignupConstant.UUID, user.getEmailUUID());
         accessTokenMapforEmail.put(OSPSignupConstant.EXPIRY_DATE, new Timestamp(new Date().getTime() + (1 * (emailExpireTime) * 60 * 60 * 1000)));
         accessTokenMapforEmail.put(OSPSignupConstant.IS_USED, 0);
         accessTokenMapforEmail.put(OSPSignupConstant.CREATED_BY, user.getUserName());
         namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
		return 0;
	}
	
	@Override
	public int generateNewSmsToken(UserBean user, int smsExpireTime)
			throws OspDaoException {
		String getUserSql = "select record_id from osp_user_password  where user_name = ?";
		int getInsertedUser = jdbcTemplate.queryForObject(getUserSql,
				new Object[] { user.getUserName() }, Integer.class);
		String insertAccessToken = "INSERT INTO osp_access_token VALUES "
				+ "(:"
				+ OSPSignupConstant.USER_ID
				+ ","
				+ ":"
				+ OSPSignupConstant.TYPE
				+ ","
				+ ":"
				+ OSPSignupConstant.UUID
				+ ","
				+ ":"
				+ OSPSignupConstant.EXPIRY_DATE
				+ ","
				+ ":"
				+ OSPSignupConstant.IS_USED
				+ ","
				+ ":"
				+ OSPSignupConstant.CREATED_BY
				+ ","
				+ ":"
				+ OSPSignupConstant.CREATED_TS
				+ ","
				+ ":"
				+ OSPSignupConstant.UPDATE_BY
				+ ","
				+ ":"
				+ OSPSignupConstant.UPDATE_TS + ");";
		Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
		accessTokenMapforSms.put(OSPSignupConstant.USER_ID, getInsertedUser);
		accessTokenMapforSms.put(OSPSignupConstant.TYPE, 21);
		accessTokenMapforSms.put(OSPSignupConstant.UUID, user.getSmsUUID());
		accessTokenMapforSms.put(OSPSignupConstant.EXPIRY_DATE, new Timestamp(
				new Date().getTime() + (1 * smsExpireTime * 60 * 60 * 1000)));
		accessTokenMapforSms.put(OSPSignupConstant.IS_USED, 0);
		accessTokenMapforSms.put(OSPSignupConstant.CREATED_BY,
				user.getUserName());
		namedJdbcTemplate.update(insertAccessToken, accessTokenMapforSms);
		return 0;
	}

	@Override
	public UserDTO checkForForgotPassword(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String emailSql = "select * from osp_user_password up , osp_access_token "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.EXPIRY_DT+ "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.USER_ID+"= up."+OSPSignupConstant.RECORD_ID; 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_name", user.getUserName());
			paramMap.put("UUID", user.getEmailUUID());
			paramMap.put("TYPE", access.getType());
			paramMap.put("EXPIRY_DT", access.getExpireTime());
			
			return namedJdbcTemplate.queryForObject(emailSql, paramMap,
					new RowMapper<UserDTO>() {
				public UserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getLong(OSPSignupConstant.RECORD_ID));
					return user;
				}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		
	}
	
	@Override
	public void updatePassword(UserBean user) throws OspDaoException {
		try {
			String updatePassword = " UPDATE osp_access_token acc , osp_user_password up "
			+ "  SET up."+OSPSignupConstant.PASSWORD +"= ? ,"
				   		+ "where up."+OSPSignupConstant.USER_NAME+" = ?"+
				   		"and  up."+OSPSignupConstant.RECORD_ID +" = ?";
			int count = jdbcTemplate.update(updatePassword, new Object[] {user.getPassword(),user.getUserName(),user.getUser_id() });
			if (count != 1) {
				throw new OspDaoException();
			}
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}


}
