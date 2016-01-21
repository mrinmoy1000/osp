package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.constant.OSPLoginConstant;
import com.flamingos.osp.constant.OSPSignupConstant;
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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.dao.LoginDao;
import com.flamingos.osp.dto.UserDTO;

@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class LoginDaoImpl implements LoginDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    
    private String loginCheckSql = "select  record_id,user_name,password,contact_number,email,activation_status from osp_user_password where user_name=:username";

	public UserDTO getUser(UserBean loginBean)throws OspDaoException {

		//List<Professional> pList=jdbcTemplate.query(sql,values,new ProfessionalMapper());
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", loginBean.getUserName());
		
		try{
		
		return namedJdbcTemplate.queryForObject(loginCheckSql, paramMap, new RowMapper<UserDTO>() {
            public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
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
		
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	@Override
    public UserBean login(UserBean usrBean) {
        String sql = "select id,username,password,contact,email from user_login  where username = ? and active_status =? or active_status=? ";
        Object[] values = new Object[]{usrBean.getUserName(), "ACTV", "APRV"};
        UserBean user = null;
        //(UserBean) jdbcTemplate.query(sql, values);
        return user;
    }

    @Override
    public UserDTO checkForUser(UserBean usrBean)throws OspDaoException {
    	String userNameSql = "SELECT * FROM osp_user_password WHERE  "+OSPLoginConstant.USER_NAME+"=:USERNAME and"+ OSPLoginConstant.EMAIL+"=:EMAIl";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("USERNAME", usrBean.getUserName());
		paramMap.put("EMAIl", usrBean.getEmail());

		try {

			return namedJdbcTemplate.queryForObject(userNameSql, paramMap,
					new RowMapper<UserDTO>() {
						public UserDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
							UserDTO user = new UserDTO();
							user.setUserId(rs.getLong(OSPSignupConstant.RECORD_ID));
							user.setUserName(rs.getString(OSPSignupConstant.USER_NAME));
							user.setUserPass(rs.getString(OSPSignupConstant.PASSWORD));
							user.setUserContact(rs.getString(OSPSignupConstant.CONTACT_NUMBER));
							user.setEmail(rs.getString(OSPSignupConstant.EMAIL));
							user.setActivationStatus(rs.getString(OSPSignupConstant.ACTIVATION_STATUS));

							return user;
						}
					});

		} catch (EmptyResultDataAccessException e) {
			return null;
		}
    }

    @Override
    public int addFUPAccessToken(UserBean user,int fupExpireTime)throws OspDaoException {

     try
     {String insertAccessToken = "INSERT INTO access_token VALUES "
     		+ "INSERT INTO osp_access_token VALUES "
    		+ "(:"+OSPSignupConstant.USER_ID+","
    		+ ":"+OSPSignupConstant.TYPE+","
    		+ ":"+OSPSignupConstant.UUID+","
    		+ ":"+OSPSignupConstant.EXPIRY_DATE+","
    		+ ":"+OSPSignupConstant.IS_USED+","
    		+ ":"+OSPSignupConstant.CREATED_TS+","
    		+ ":"+OSPSignupConstant.UPDATED_TS+","
    		+ ":"+OSPSignupConstant.CREATED_BY+","
    		+ ":"+OSPSignupConstant.UPDATED_BY+")";
        Map<String, Object> accessTokenMapforFUP = new HashMap<String, Object>();
        accessTokenMapforFUP.put(OSPSignupConstant.USER_ID,user.getUser_id());
        accessTokenMapforFUP.put(OSPSignupConstant.TYPE, 0);
        accessTokenMapforFUP.put(OSPSignupConstant.UUID, user.getSmsUUID());
        accessTokenMapforFUP.put(OSPSignupConstant.EXPIRY_DATE, new Timestamp(new Date().getTime()+(1 * fupExpireTime  * 60 * 60 * 1000)));
        accessTokenMapforFUP.put(OSPSignupConstant.IS_USED, 0);
        accessTokenMapforFUP.put(OSPSignupConstant.CREATED_TS,new Timestamp(new Date().getTime()));
        accessTokenMapforFUP.put(OSPSignupConstant.UPDATE_TS, null);
        accessTokenMapforFUP.put(OSPSignupConstant.CREATED_BY, user.getUserName());	           
        accessTokenMapforFUP.put(OSPSignupConstant.UPDATE_BY, null);
        int count = namedJdbcTemplate.update(insertAccessToken, accessTokenMapforFUP);
        return count;
     }
        catch (RuntimeException e) {
			throw new OspDaoException();
		}

    }
	
}
