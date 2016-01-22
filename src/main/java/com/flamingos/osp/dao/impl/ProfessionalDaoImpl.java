package com.flamingos.osp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPSignupConstant;
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
	public void emailUpdateStatus(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String updateEmailStatusSql = " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up "
			+ "  SET up."+OSPSignupConstant.ACTIVATION_STATUS +"= ? ,"
				   +"up."+OSPSignupConstant.EMAIL_VERIFIED +" = ?  "
				   		+ "where up."+OSPSignupConstant.USER_NAME+" = ?"+
				   "and acc."+OSPSignupConstant.UUID+" = ?"+
				   		"and  up."+OSPSignupConstant.RECORD_ID +" = acc."+OSPSignupConstant.RECORD_ID;
			int count = jdbcTemplate.update(updateEmailStatusSql, new Object[] {
					user.getActiveStatus(), user.getEmailVerified(), user.getUserName(), user.getEmailUUID() });
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
			String updateSmsStatusSql = " UPDATE OSP_ACCESS_TOKEN acc , OSP_USER_CREDENTIAL up "
					+ "  SET up."+OSPSignupConstant.ACTIVATION_STATUS +"= ? ,"
					   +"up."+OSPSignupConstant.SMS_VERIFIED +" = ?  "
					   		+ "where up."+OSPSignupConstant.USER_NAME+" = ?"+
					   "and acc."+OSPSignupConstant.UUID+" = ?"+
					   		" and  up."+OSPSignupConstant.RECORD_ID +" = acc."+OSPSignupConstant.RECORD_ID;
			int count = jdbcTemplate.update(
					updateSmsStatusSql,
					new Object[] { user.getActiveStatus(),
							user.getSmsVerfied(), user.getUserName(),
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
			String emailSql = "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " up."+OSPSignupConstant.ACTIVATION_STATUS+"=:activation_status and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.TOKEN_EXPIRY_DT+ "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.RECORD_ID+"= up."+OSPSignupConstant.RECORD_ID; 
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

		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public UserDTO getUserLinkValidCheckForFUP(UserBean user, AccessToken access)
			throws OspDaoException {
		try {
			String emailSql = "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " up."+OSPSignupConstant.ACTIVATION_STATUS+"=:activation_status and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.TOKEN_EXPIRY_DT+ "> :EXPIRY_DT"
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
			String emailSql = "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " up."+OSPSignupConstant.ACTIVATION_STATUS+"=:activation_status and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.TOKEN_EXPIRY_DT + "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.RECORD_ID+"= up."+OSPSignupConstant.RECORD_ID;
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

		} catch (RuntimeException e) {
			return null;
		}
	}


	@Override
	public List<UserBean> getTokenCheck(UserBean user, AccessToken access)
			throws OspDaoException {
		List<UserBean> userTokenList = null;
		try {

			String getUserSql = "select * from OSP_USER_CREDENTIAL up , osp_access_token acc , osp_parameter param"
					+ " where acc."+OSPSignupConstant.USER_ID+" = up."+OSPSignupConstant.RECORD_ID+
					 " and param."+OSPSignupConstant.PARAM_ID+" = acc."+OSPSignupConstant.TYPE 
					 + "and  up."+OSPSignupConstant.USER_NAME+"= ? and "+
					" acc."+OSPSignupConstant.CARD_EXPIRY_DATE+" > ?";
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("username", user.getUserName());
			paramMap.put("EXPIRY_DT", new Date().getTime());
			userTokenList = namedJdbcTemplate.query(getUserSql, paramMap,new RowMapper<UserBean>() {
				public UserBean mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					UserBean user = new UserBean();
					user.setTokenType(rs.getString(OSPSignupConstant.PARAM_VALUE));
					user.setEmailVerified(rs.getInt(OSPSignupConstant.EMAIL_VERIFIED));
					user.setSmsVerfied(rs.getInt(OSPSignupConstant.SMS_VERIFIED));
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
		 String getUserSql = "select record_id from OSP_USER_CREDENTIAL  where user_name = ?";
         int getInsertedUser = jdbcTemplate.queryForObject(getUserSql, new Object[]{user.getUserName()}, Integer.class);
		 String insertAccessToken = "INSERT INTO osp_access_token VALUES "
		 		+ "(:"+OSPSignupConstant.USER_ID+","
		 		+ ":"+OSPSignupConstant.TYPE+","
		 		+ ":"+OSPSignupConstant.UUID+","
		 		+ ":"+OSPSignupConstant.TOKEN_EXPIRY_DT+","
		 		+ ":"+OSPSignupConstant.IS_USED+","
		 		+ ":"+OSPSignupConstant.CREATED_BY+","
		 		+ ":"+OSPSignupConstant.CREATED_TS+","
		 		+ ":"+OSPSignupConstant.UPDATE_BY+","
		 		+ ":"+OSPSignupConstant.UPDATE_TS+");";
         Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
         accessTokenMapforEmail.put(OSPSignupConstant.USER_ID, getInsertedUser);
         accessTokenMapforEmail.put(OSPSignupConstant.TYPE,22);
         accessTokenMapforEmail.put(OSPSignupConstant.UUID, user.getEmailUUID());
         accessTokenMapforEmail.put(OSPSignupConstant.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime() + (1 * (emailExpireTime) * 60 * 60 * 1000)));
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
				+ OSPSignupConstant.TOKEN_EXPIRY_DT
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
		accessTokenMapforSms.put(OSPSignupConstant.TOKEN_EXPIRY_DT	, new Timestamp(
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
			String emailSql = "select * from OSP_USER_CREDENTIAL up , OSP_ACCESS_TOKEN "
					+ " acc  where"
					+ " up."+OSPSignupConstant.USER_NAME+"= :user_name and "
					+ " acc."+OSPSignupConstant.UUID+"=:UUID and"
					+ " acc."+OSPSignupConstant.TYPE+"=:TYPE"
					+ " and  acc."+OSPSignupConstant.TOKEN_EXPIRY_DT+ "> :EXPIRY_DT"
					+ " and  acc."+OSPSignupConstant.RECORD_ID+"= up."+OSPSignupConstant.RECORD_ID; 
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("user_name", user.getUserName());
			paramMap.put("UUID", user.getFupUUID());
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

		} catch (RuntimeException e) {
			throw new OspDaoException();
		}
		
	}
	
	@Override
	public void updatePassword(UserBean user) throws OspDaoException {
		try {
			String updatePassword = " UPDATE  OSP_USER_CREDENTIAL up "
			+ "  SET up."+OSPSignupConstant.PASSWORD +"= ?  "
				   		+ "where  up."+OSPSignupConstant.RECORD_ID +" = ?";
			int count = jdbcTemplate.update(updatePassword, new Object[] {user.getPassword(),user.getUser_id() });
			if (count != 1) {
				throw new OspDaoException();
			}
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}
	@Override
	public int addProfile(OspProfessionalBean professionalBean) throws OspDaoException {
		try {
			final String INSERT_SQL = "INSERT INTO OSP_PROFESSIONAL(RECORD_ID,PROF_FIRST_NAME,PROF_MIDDLE_NAME,PROF_LAST_NAME,PROF_EMP_ID,PROF_DOB,PROF_GENDER,PROF_NATIONALITY,PROF_PAN,PROF_MERITGAL_STATUS,PROF_MERRIAGE_ANNIVERSARY,DND_ACTIVATED_FLAG,PROF_SIGNATURE,PROF_SUBSC_ID,PROF_PUBLIC_ID,PROF_FEES,PROF_REMARK,STATUS,CREATED_TS,CREATED_BY) VALUES (:RECORD_ID,:PROF_FIRST_NAME,:PROF_MIDDLE_NAME,:PROF_LAST_NAME,:PROF_EMP_ID,:PROF_DOB,:PROF_GENDER,:PROF_NATIONALITY,:PROF_PAN,:PROF_MERITGAL_STATUS,:PROF_MERRIAGE_ANNIVERSARY,:DND_ACTIVATED_FLAG,:PROF_SIGNATURE,:PROF_SUBSC_ID,:PROF_PUBLIC_ID,:PROF_FEES,:PROF_REMARK,:STATUS,:CREATED_TS,:CREATED_BY)";
			
			final int record_id=professionalBean.getRecord_id();
			final String prof_first_name=professionalBean.getProf_first_name();
			final String prof_middle_name=professionalBean.getProf_middle_name();
			final String prof_last_name=professionalBean.getProf_last_name();
			final String prof_emp_id=professionalBean.getProf_emp_id();
			final String prof_dob=professionalBean.getProf_dob();
			final String prof_gender=professionalBean.getProf_gender();
			final String prof_nationality=professionalBean.getProf_nationality();
			final String prof_pan=professionalBean.getProf_pan();
			final String prof_merital_status=professionalBean.getProf_merital_status();
			final String prof_merriage_anniversary=professionalBean.getProf_merriage_anniversary();
			final int dnd_activated_flag=professionalBean.getDnd_activated_flag();
			final String prof_signature=professionalBean.getProf_signature();	
			final String prof_subsc_id=professionalBean.getProf_subsc_id();
			final String prof_public_id=professionalBean.getProf_public_id();
			final double prof_fees=professionalBean.getProf_fees();
			final String prof_remark=professionalBean.getProf_remark();
			final int status=professionalBean.getStatus();
			final Timestamp created_ts=new Timestamp(new Date().getTime());	    	
			final String created_by=professionalBean.getCreated_by();
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps =connection.prepareStatement(INSERT_SQL, new String[] {"prof_id"});
			            ps.setInt(1, record_id);
			            ps.setString(2, prof_first_name);
			            ps.setString(3, prof_middle_name);
			            ps.setString(4, prof_last_name);
			            ps.setString(5, prof_emp_id);
			            ps.setString(6, prof_dob);
			            ps.setString(7, prof_gender);
			            ps.setString(8, prof_nationality);
			            ps.setString(9, prof_pan);
			            ps.setString(10, prof_merital_status);
			            ps.setString(11, prof_merriage_anniversary);
			            ps.setInt(12, dnd_activated_flag);
			            ps.setString(13, prof_signature);
			            ps.setString(14, prof_subsc_id);
			            ps.setString(15, prof_public_id);
			            ps.setDouble(16, prof_fees);
			            ps.setString(17, prof_remark);
			            ps.setInt(18, status);
			            ps.setTimestamp(19, created_ts);
			            ps.setString(20, created_by);
			            return ps;
			        }
			    },
			    keyHolder);
			
			professionalBean.setProf_id((Integer)keyHolder.getKey());
						
			
			return 0;
		} catch (RuntimeException exp) {
			throw new OspDaoException();
		}
	}
	
	@Override
	public void approveProfile(OspProfessionalBean professionalBean) throws OspDaoException {
		
	}

}
