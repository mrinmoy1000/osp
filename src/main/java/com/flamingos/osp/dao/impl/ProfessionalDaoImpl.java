package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
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
					+ "  SET up.activation_status = ? ,up.email_veryfied = ?"
					+ " where up.user_name = ? and acc.UUID = ? and  up.record_id = acc.USER_ID";
			int count = jdbcTemplate.update(updateEmailStatusSql, new Object[] {
					1, 1, user.getUserName(), user.getEmailUUID() });
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
					+ "  SET active_indicator = ? ,SMSVERIFIED = ?"
					+ " where ul.username = ? and acc.uuid = ? and  ul.id = acc.userid";
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
					+ "  SET active_indicator = ? "
					+ " where ul.username = ? and acc.uuid = ? and on up.id = acc.userid";
			int count = jdbcTemplate.update(
					updateEmailStatusSql,
					new Object[] { access.getActiveIndicator(),
							user.getEmailVerified(), user.getUserName(),
							user.getFupUUID() });
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
					+ " acc  where up.user_name = :user_name and "
					+ " acc.uuid=:UUID and"
					+ " up.activation_status=:activation_status and"
					+ " acc.TYPE=:TYPE and  acc.EXPIRY_DT > :EXPIRY_DT and  acc.user_id= up.record_id"; 
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
							user.setUserId(rs.getLong("record_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPass(rs.getString("password"));
							user.setUserContact(rs.getString("contact_number"));
							user.setEmail(rs.getString("email"));
							user.setActivationStatus(rs.getString("activation_status"));

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
					+ " acc  where up.user_name = :user_name and "
					+ " acc.uuid=:UUID and"
					+ " up.activation_status=:activation_status and"
					+ " acc.TYPE=:TYPE and  acc.EXPIRY_DT < :EXPIRY_DT and on acc.user_id= up.record_id"; 
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
							user.setUserId(rs.getLong("record_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPass(rs.getString("password"));
							user.setUserContact(rs.getString("contact_number"));
							user.setEmail(rs.getString("email"));
							user.setActivationStatus(rs.getString("activation_status"));

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
					+ " acc  where up.user_name = :user_name and "
					+ " acc.uuid=:UUID and"
					+ " up.activation_status=:activation_status and"
					+ " acc.TYPE=:TYPE and  acc.EXPIRY_DT < :EXPIRY_DT and on acc.user_id= up.record_id"; 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_name", user.getSmsUUID());
			paramMap.put("UUID", user.getEmailUUID());
			paramMap.put("activation_status", access.getActiveIndicator());
			paramMap.put("TYPE", access.getType());
			paramMap.put("EXPIRY_DT", access.getExpireTime());
			
			return namedJdbcTemplate.queryForObject(emailSql, paramMap,
					new RowMapper<UserDTO>() {
						public UserDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserDTO user = new UserDTO();
							user.setUserId(rs.getLong("record_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPass(rs.getString("password"));
							user.setUserContact(rs.getString("contact_number"));
							user.setEmail(rs.getString("email"));
							user.setActivationStatus(rs.getString("activation_status"));

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
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", user.getUserName());
			paramMap.put("EXPIRY_DT", new Date().getTime());
			String getUserSql = "select acc.PARAM_VALUE,up.email_veryfied,up.sms_verified,acc.UUID from osp_user_password up , osp_access_token acc , osp_parameter param"
					+ " where acc.user_id= up.record_id and param.PARAM_ID=acc.TYPE and  up.user_name = ? and "
					+ " acc.expire_time > ?";
			
			userTokenList = namedJdbcTemplate.query(getUserSql, paramMap,new RowMapper<UserBean>() {
				public UserBean mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserBean user = new UserBean();
					user.setTokenType(rs.getString("PARAM_VALUE"));
					user.setEmailVerified(rs.getString("email_veryfied"));
					user.setSmsVerfied(rs.getString("sms_verified"));
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
		 String insertAccessToken = "INSERT INTO osp_access_token VALUES (:user_id,:type,:uuid,:expiry_ts,:is_used,:created_by,:created_ts,:updated_by,:updated_ts);";
         Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
         accessTokenMapforEmail.put("userid", getInsertedUser);
         accessTokenMapforEmail.put("type",22);
         accessTokenMapforEmail.put("uuid", user.getEmailUUID());
         accessTokenMapforEmail.put("expire_time", new Timestamp(new Date().getTime() + (1 * (emailExpireTime) * 60 * 60 * 1000)));
         accessTokenMapforEmail.put("is_used", 0);
         accessTokenMapforEmail.put("created_by", user.getUserName());
         namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
		return 0;
	}
	
	@Override
	public int generateNewSmsToken(UserBean user, int smsExpireTime)
			throws OspDaoException {
		 String getUserSql = "select record_id from osp_user_password  where user_name = ?";
         int getInsertedUser = jdbcTemplate.queryForObject(getUserSql, new Object[]{user.getUserName()}, Integer.class);
		 String insertAccessToken = "INSERT INTO osp_access_token VALUES (:user_id,:type,:uuid,:expiry_ts,:is_used,:created_by,:created_ts,:updated_by,:updated_ts);";
         Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
         accessTokenMapforSms.put("userid", getInsertedUser);
         accessTokenMapforSms.put("type",21);
         accessTokenMapforSms.put("uuid", user.getSmsUUID());
         accessTokenMapforSms.put("expire_time", new Timestamp(new Date().getTime() + (1 * smsExpireTime * 60 * 60 * 1000)));
         accessTokenMapforSms.put("is_used", 0);
         accessTokenMapforSms.put("created_by", user.getUserName());
         namedJdbcTemplate.update(insertAccessToken, accessTokenMapforSms);
		return 0;
	}


}
