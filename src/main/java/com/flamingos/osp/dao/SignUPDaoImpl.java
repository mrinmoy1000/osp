package com.flamingos.osp.dao;

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
	public int createNewUser(UserBean user) throws OspDaoException {
		  String sql = "INSERT INTO user_login VALUES (:id,:username,:password,:contact,:email,:active_status,:insert_ts,:SMSVERIFIED,:EMAILVERIFIED,:USERTYPECD);";
	        Map<String, Object> userDetailsMap = new HashMap<String, Object>();
	        userDetailsMap.put("id", user.getId());
	        userDetailsMap.put("username", user.getUserName());
	        userDetailsMap.put("password", user.getPassword());
	        userDetailsMap.put("contact", user.getContactNumber());
	        userDetailsMap.put("email", user.getEmail());
	        userDetailsMap.put("active_status", user.getActiveStatus());
	        userDetailsMap.put("insert_ts", new Timestamp(new Date().getTime()));
	        userDetailsMap.put("SMSVERIFIED", user.getSmsVerfied());
	        userDetailsMap.put("EMAILVERIFIED", user.getEmailVerified());
	        userDetailsMap.put("USERTYPECD", user.getUserTypeCD());
	        int count = namedJdbcTemplate.update(sql, userDetailsMap);
	        if (count > 0) {
	            String getUserSql = "select id from user_login  where username = ?";
	            int getInsertedUser = jdbcTemplate.queryForObject(getUserSql, new Object[]{user.getUserName()}, Integer.class);
	            String insertAccessToken = "INSERT INTO access_token VALUES (:id,:userid,:type,:uuid,:expire_time,:active_indicator);";
	            Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
	            accessTokenMapforEmail.put("id", 0);
	            accessTokenMapforEmail.put("userid", getInsertedUser);
	            accessTokenMapforEmail.put("type", "mail");
	            accessTokenMapforEmail.put("uuid", user.getUUID());
	            accessTokenMapforEmail.put("expire_time", new Timestamp(new Date().getTime() + (1 * 24 * 60 * 60 * 1000)));
	            accessTokenMapforEmail.put("active_indicator", "Y");
	            namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
	            Map<String, Object> accessTokenMapforSms = new HashMap<String, Object>();
	            accessTokenMapforSms.put("id", 0);
	            accessTokenMapforSms.put("userid", getInsertedUser);
	            accessTokenMapforSms.put("type", "sms");
	            accessTokenMapforSms.put("uuid", user.getUUID());
	            accessTokenMapforSms.put("expire_time", new Timestamp(new Date().getTime() + (1 * 24 * 60 * 60 * 1000)));
	            accessTokenMapforSms.put("active_indicator", "Y");
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
