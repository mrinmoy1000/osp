package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.util.AppConstants;
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
    	String userNameSql = "SELECT * FROM OSP_USER_CREDENTIAL WHERE  "+AppConstants.USER_NAME+"=:USERNAME and " + AppConstants.EMAIL+"=:EMAIl";
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("USERNAME", usrBean.getUserName());
		paramMap.put("EMAIl", usrBean.getEmail());

		try {

			return namedJdbcTemplate.queryForObject(userNameSql, paramMap,
					new RowMapper<UserDTO>() {
						public UserDTO mapRow(ResultSet rs, int rowNum)
								throws SQLException {
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
			return null;
		}
    }

    @Override
    public int addFUPAccessToken(UserBean user,int fupExpireTime)throws OspDaoException {

     try
     {String insertAccessToken = "INSERT INTO OSP_ACCESS_TOKEN VALUES "
    		+ "(:"+AppConstants.USER_ID+","
    		+ ":"+AppConstants.TYPE+","
    		+ ":"+AppConstants.UUID+","
    		+ ":"+AppConstants.TOKEN_EXPIRY_DT+","
    		+ ":"+AppConstants.IS_USED+","
    		+ ":"+AppConstants.CREATED_TS+","
    		+ ":"+AppConstants.UPDATED_TS+","
    		+ ":"+AppConstants.CREATED_BY+","
    		+ ":"+AppConstants.UPDATED_BY+")";
        Map<String, Object> accessTokenMapforFUP = new HashMap<String, Object>();
        accessTokenMapforFUP.put(AppConstants.USER_ID,user.getUser_id());
        accessTokenMapforFUP.put(AppConstants.TYPE, 0);
        accessTokenMapforFUP.put(AppConstants.UUID, user.getFupUUID());
        accessTokenMapforFUP.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime()+(1 * fupExpireTime  * 60 * 60 * 1000)));
        accessTokenMapforFUP.put(AppConstants.IS_USED, 0);
        accessTokenMapforFUP.put(AppConstants.CREATED_TS,new Timestamp(new Date().getTime()));
        accessTokenMapforFUP.put(AppConstants.UPDATE_TS, null);
        accessTokenMapforFUP.put(AppConstants.CREATED_BY, user.getUserName());	           
        accessTokenMapforFUP.put(AppConstants.UPDATE_BY, null);
        int count = namedJdbcTemplate.update(insertAccessToken, accessTokenMapforFUP);
        return count;
     }
        catch (RuntimeException e) {
			throw new OspDaoException();
		}

    }
	
}
