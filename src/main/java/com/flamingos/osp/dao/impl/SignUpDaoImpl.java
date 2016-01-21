package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPLoginConstant;
import com.flamingos.osp.constant.OSPSignupConstant;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.exception.OspServiceException;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.UserDTO;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class SignUpDaoImpl implements SignUpDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private String getUserSql = "SELECT  RECORD_ID,USER_NAME,PASSWORD,CONTACT_NUMBER,EMAIL,ACTIVATION_STATUS FROM osp_user_password WHERE ";

	@Override
	public UserDTO findByUserName(String userName) throws OspDaoException {
		String userNameSql = getUserSql + " USER_NAME=:username";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", userName);

		try {

			return namedJdbcTemplate.queryForObject(userNameSql, paramMap,
					new RowMapper<UserDTO>() {
						public UserDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserDTO user = new UserDTO();
							user.setUserId(rs.getLong(OSPLoginConstant.RECORD_ID));
							user.setUserName(rs.getString(OSPLoginConstant.USER_NAME));
							user.setUserPass(rs.getString(OSPLoginConstant.PASSWORD));
							user.setUserContact(rs.getString(OSPLoginConstant.CONTACT_NUMBER));
							user.setEmail(rs.getString(OSPLoginConstant.EMAIL));
							user.setActivationStatus(rs
									.getString(OSPLoginConstant.ACTIVATION_STATUS));

							return user;
						}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public UserDTO findByContact(Long contact) throws OspDaoException {

		String contactSql = getUserSql + " contact_number=:contact";
		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("contact", contact);

		try {

			return namedJdbcTemplate.queryForObject(contactSql, paramMap,
					new RowMapper<UserDTO>() {
				public UserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getLong(OSPLoginConstant.RECORD_ID));
					user.setUserName(rs.getString(OSPLoginConstant.USER_NAME));
					user.setUserPass(rs.getString(OSPLoginConstant.PASSWORD));
					user.setUserContact(rs.getString(OSPLoginConstant.CONTACT_NUMBER));
					user.setEmail(rs.getString(OSPLoginConstant.EMAIL));
					user.setActivationStatus(rs
							.getString(OSPLoginConstant.ACTIVATION_STATUS));

					return user;
				}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public UserDTO findByEmailAddress(String email)throws OspDaoException {

		String emailSql = getUserSql + " email=:email";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("email", email);

		try {

			return namedJdbcTemplate.queryForObject(emailSql, paramMap,
					new RowMapper<UserDTO>() {
				public UserDTO mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserDTO user = new UserDTO();
					user.setUserId(rs.getLong(OSPLoginConstant.RECORD_ID));
					user.setUserName(rs.getString(OSPLoginConstant.USER_NAME));
					user.setUserPass(rs.getString(OSPLoginConstant.PASSWORD));
					user.setUserContact(rs.getString(OSPLoginConstant.CONTACT_NUMBER));
					user.setEmail(rs.getString(OSPLoginConstant.EMAIL));
					user.setActivationStatus(rs
							.getString(OSPLoginConstant.ACTIVATION_STATUS));

					return user;
				}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public int createNewUser(UserBean user,int emailExpireTime,int smsExpireTime) throws OspDaoException {
	        Map<String, Object> userDetailsMap = new HashMap<String, Object>();	       
	        userDetailsMap.put(OSPSignupConstant.USER_NAME, user.getUserName());
	        userDetailsMap.put(OSPSignupConstant.ROLE_ID, 2);
	        userDetailsMap.put(OSPSignupConstant.RECORD_ID, 0);	      
	        userDetailsMap.put(OSPSignupConstant.RECORD_TYPE, 12);
	        userDetailsMap.put(OSPSignupConstant.PASSWORD, user.getPassword());
	        userDetailsMap.put(OSPSignupConstant.SALT, "salt");
	        userDetailsMap.put(OSPSignupConstant.EXPIRY_DATE, new Timestamp(new Date().getTime()));
	        userDetailsMap.put(OSPSignupConstant.NO_OF_ATTEMPTS, 1);
	        userDetailsMap.put(OSPSignupConstant.EMAIL_VERIFIED, 0);
	        userDetailsMap.put(OSPSignupConstant.SMS_VERIFIED, 0);
	        userDetailsMap.put(OSPSignupConstant.ACTIVATION_STATUS, 0);
	        userDetailsMap.put(OSPSignupConstant.CREATED_BY, user.getUserName());
	        userDetailsMap.put(OSPSignupConstant.CREATED_TS, new Timestamp(new Date().getTime()));
	        userDetailsMap.put(OSPSignupConstant.UPDATE_BY, null);
	        userDetailsMap.put(OSPSignupConstant.UPDATE_TS, null);
	        userDetailsMap.put(OSPSignupConstant.CONTACT_NUMBER, user.getContactNumber());
	        userDetailsMap.put(OSPSignupConstant.EMAIL, user.getEmail());
	        userDetailsMap.put(OSPSignupConstant.FIRST_NAME, user.getFirstName());
	        userDetailsMap.put(OSPSignupConstant.MIDDLE_NAME, user.getMiddleName());
	        userDetailsMap.put(OSPSignupConstant.LAST_NAME, user.getLastName());
	        userDetailsMap.put(OSPSignupConstant.LOGIN_TS, new Timestamp(new Date().getTime()));
	        SimpleJdbcInsert simpleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("osp_user_password").usingGeneratedKeyColumns("record_id");
	        Number  generateKey =  simpleInsert.executeAndReturnKey(userDetailsMap);
	        long getInsertedUser = (Long)generateKey;
	        if (getInsertedUser != 0) {
	            String insertAccessToken = "INSERT INTO osp_access_token VALUES (:USER_ID,:TYPE,:UUID,:EXPIRY_DT,:IS_USED,:CREATED_TS,:UPDATED_TS,:CREATED_BY,:UPDATED_BY);";
	            Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
	            accessTokenMapforEmail.put(OSPSignupConstant.USER_ID,getInsertedUser);
	            accessTokenMapforEmail.put(OSPSignupConstant.TYPE, 0);
	            accessTokenMapforEmail.put(OSPSignupConstant.UUID,user.getEmailUUID());
	            accessTokenMapforEmail.put(OSPSignupConstant.EXPIRY_DATE, new Timestamp(new Date().getTime()+(1 * emailExpireTime  * 60 * 60 * 1000)));
	            accessTokenMapforEmail.put(OSPSignupConstant.IS_USED, 0);
	            accessTokenMapforEmail.put(OSPSignupConstant.CREATED_TS,new Timestamp(new Date().getTime()));
	            accessTokenMapforEmail.put(OSPSignupConstant.UPDATE_TS, null);	 
	            accessTokenMapforEmail.put(OSPSignupConstant.CREATED_BY, user.getUserName());         
	            accessTokenMapforEmail.put(OSPSignupConstant.UPDATE_BY, null);
	            namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
	            Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
	            accessTokenMapforSms.put(OSPSignupConstant.USER_ID,getInsertedUser);
	            accessTokenMapforSms.put(OSPSignupConstant.TYPE, 0);
	            accessTokenMapforSms.put(OSPSignupConstant.UUID, user.getSmsUUID());
	            accessTokenMapforSms.put(OSPSignupConstant.EXPIRY_DATE, new Timestamp(new Date().getTime()+(1 * smsExpireTime  * 60 * 60 * 1000)));
	            accessTokenMapforSms.put(OSPSignupConstant.IS_USED, 0);
	            accessTokenMapforSms.put(OSPSignupConstant.CREATED_TS,new Timestamp(new Date().getTime()));
	            accessTokenMapforSms.put(OSPSignupConstant.UPDATE_TS, null);
	            accessTokenMapforSms.put(OSPSignupConstant.CREATED_BY, user.getUserName());	           
	            accessTokenMapforSms.put(OSPSignupConstant.UPDATE_BY, null);
	            namedJdbcTemplate.update(insertAccessToken, accessTokenMapforSms);
	        } else {
	           throw new EmptyResultDataAccessException(1);
	        }
		return 0;
	}

	@Override
	public String updateUser(UserBean user) throws OspDaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO checkForProfessional(UserBean user) throws OspDaoException {

		
		String profSql = "select * from osp_professional  where PROF_ID=:PROF_ID";
		Map<String, Long> paramMap = new HashMap<String, Long>();
		paramMap.put("PROF_ID", user.getProf_id());

		try {

			return namedJdbcTemplate.queryForObject(profSql, paramMap,
					new RowMapper<UserDTO>() {
						public UserDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserDTO user = new UserDTO();
							user.setUserId(rs.getLong("PROF_ID"));
							user.setUserName(rs.getString("PROF_FIRST_NAME"));
							return user;
						}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void mapUserAndProfessional(long recordId,long profId) throws OspDaoException {
		try {
			String updateEmailStatusSql = " UPDATE osp_professional prof  "
					+ "  SET RECORD_ID = ? where prof.PROF_ID = ?";
			int count = jdbcTemplate.update(updateEmailStatusSql, new Object[] {
					recordId, profId });
			if (count != 1) {
				throw new OspDaoException();
			}
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}
}
