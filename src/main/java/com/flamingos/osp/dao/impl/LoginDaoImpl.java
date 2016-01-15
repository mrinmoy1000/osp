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

import com.flamingos.osp.dto.UserDTO;

@Transactional(propagation = Propagation.REQUIRED)
public class LoginDaoImpl {

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
	
    public UserBean login(UserBean usrBean) {
        String sql = "select id,username,password,contact,email from user_login  where username = ? and active_status =? or active_status=? ";
        Object[] values = new Object[]{usrBean.getUserName(), "ACTV", "APRV"};
        UserBean user = null;
        //(UserBean) jdbcTemplate.query(sql, values);
        return user;
    }

    public int checkForUser(UserBean usrBean)throws OspDaoException {
        String sql1 = "select count(*) from user_login  where username = ? ";
        Object[] value1 = new Object[]{usrBean.getUserName()};
        @SuppressWarnings("deprecation")
		int countForUser = jdbcTemplate.queryForInt(sql1, value1);
        return countForUser;
    }

    public String addFUPAccessToken(UserBean user) {

        String getUserSql = "select id from user_login  where username = ?";
        int getInsertedUser = jdbcTemplate.queryForObject(getUserSql, new Object[]{user.getUserName()}, Integer.class);
        String insertAccessToken = "INSERT INTO access_token VALUES (:id,:userid,:type,:uuid,:expire_time,:active_indicator);";
        Map<String, Object> accessTokenMapforEmail = new HashMap<String, Object>();
        accessTokenMapforEmail.put("id", 0);
        accessTokenMapforEmail.put("userid", getInsertedUser);
        accessTokenMapforEmail.put("type", "fup");
       // accessTokenMapforEmail.put("uuid", user.getUUID());
        accessTokenMapforEmail.put("expire_time", new Timestamp(new Date().getTime() + (1 * 4 * 60 * 60 * 1000)));
        accessTokenMapforEmail.put("active_indicator", "Y");
        int count = namedJdbcTemplate.update(insertAccessToken, accessTokenMapforEmail);
        if (count > 0) {
            return "success";
        } else {
            return "error";
        }

    }
}
