package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspDaoException;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.dao.SignUpDao;
import com.flamingos.osp.dto.UserDTO;

@Transactional(propagation = Propagation.REQUIRED)
public class SignUPDaoImpl implements SignUpDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	private String getUserSql = "select  record_id,user_name,password,contact_number,email,activation_status from osp_user_password where ";

	@Override
	public UserDTO findByUserName(String userName) throws OspDaoException {
		String userNameSql = getUserSql + " user_name=:username";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", userName);

		try {

			return namedJdbcTemplate.queryForObject(userNameSql, paramMap,
					new RowMapper<UserDTO>() {
						public UserDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserDTO user = new UserDTO();
							user.setUserId(rs.getLong("record_id"));
							user.setUserName(rs.getString("user_name"));
							user.setUserPass(rs.getString("password"));
							user.setUserContact(rs.getString("contact_number"));
							user.setEmail(rs.getString("email"));
							user.setActivationStatus(rs
									.getString("activation_status"));

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
		paramMap.put("username", contact);

		try {

			return namedJdbcTemplate.queryForObject(contactSql, paramMap,
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
	public UserDTO findByEmailAddress(String email)throws OspDaoException {

		String emailSql = getUserSql + " email=:email";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", email);

		try {

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
	public int createNewUser(UserBean user,String emailExpireTime,String smsExpireTime) throws OspDaoException {
		  String sql = "INSERT INTO osp_user_password VALUES (:user_name,:role_id,:record_type,:password,:email_veryfied,:sms_verified,:activation_status,:created_by,:created_ts,:email,:first_name,:middle_name,:last_name,:login_ts);";
	        Map<String, Object> userDetailsMap = new HashMap<String, Object>();
	        userDetailsMap.put("user_name", user.getUserName());
	        userDetailsMap.put("role_id", 2);
	        userDetailsMap.put("record_type", user.getRecord_type());
	        userDetailsMap.put("password", user.getPassword());
	        //userDetailsMap.put("salt", );
	        //userDetailsMap.put("expiry_date", new Timestamp(new Date().getTime()));
	        //userDetailsMap.put("no_of_attempts", user.getSmsVerfied());
	        userDetailsMap.put("email_veryfied", "N");
	        userDetailsMap.put("sms_verified", "N");
	        userDetailsMap.put("activation_status", "I");
	        userDetailsMap.put("created_by", user.getUserName());
	        userDetailsMap.put("created_ts", new Timestamp(new Date().getTime()));
	     //   userDetailsMap.put("updated_by", user.getUserTypeCD());
	     //   userDetailsMap.put("contact_number", user.getUserTypeCD());
	        userDetailsMap.put("email", user.getEmail());
	        userDetailsMap.put("first_name", user.getFirstName());
	        userDetailsMap.put("middle_name", user.getMiddleName());
	        userDetailsMap.put("last_name", user.getLastName());
	        userDetailsMap.put("login_ts", new Timestamp(new Date().getTime()));
	        int count = namedJdbcTemplate.update(sql, userDetailsMap);
	        if (count > 0) {
	            String getUserSql = "select record_id from osp_user_password  where user_name = ?";
	            int getInsertedUser = jdbcTemplate.queryForObject(getUserSql, new Object[]{user.getUserName()}, Integer.class);
	            String insertAccessToken = "INSERT INTO osp_access_token VALUES (:user_id,:type,:uuid,:expiry_ts,:is_used,:created_by,:created_ts,:updated_by,:updated_ts);";
	            Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
	            accessTokenMapforEmail.put("userid", getInsertedUser);
	            accessTokenMapforEmail.put("type", 0);
	            accessTokenMapforEmail.put("uuid", user.getEmailUUID());
	            accessTokenMapforEmail.put("expire_time", new Timestamp(new Date().getTime() + (1 * Integer.parseInt(emailExpireTime) * 60 * 60 * 1000)));
	            accessTokenMapforEmail.put("is_used", 0);
	            accessTokenMapforEmail.put("created_by", user.getUserName());
	            accessTokenMapforEmail.put("created_ts", new Timestamp(new Date().getTime()));
	           // accessTokenMapforEmail.put("updated_by", "Y");
	          //  accessTokenMapforEmail.put("updated_ts", "Y");
	            namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
	            Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
	            accessTokenMapforEmail.put("userid", getInsertedUser);
	            accessTokenMapforEmail.put("type", 1);
	            accessTokenMapforEmail.put("uuid", user.getSmsUUID());
	            accessTokenMapforEmail.put("expire_time", new Timestamp(new Date().getTime() + (1 * Integer.parseInt(smsExpireTime) * 60 * 60 * 1000)));
	            accessTokenMapforEmail.put("is_used", 0);
	            accessTokenMapforEmail.put("created_by", user.getUserName());
	            accessTokenMapforEmail.put("created_ts", new Timestamp(new Date().getTime()));
	          //  accessTokenMapforEmail.put("updated_by", "Y");
	          //  accessTokenMapforEmail.put("updated_ts", "Y");
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
}
