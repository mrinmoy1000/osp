package com.flamingos.osp.dao;

import com.flamingos.osp.bean.AccessToken;
import com.flamingos.osp.bean.UserBean;
import com.flamingos.osp.exception.OspDaoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Transactional(propagation = Propagation.REQUIRED)
public class ProfessionalDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public String checkUniqueness(UserBean usrBean)throws OspDaoException{
        String sql1 = "select count(*) from user_login  where username = ? ";
        Object[] value1 = new Object[]{usrBean.getUserName()};
        @SuppressWarnings("deprecation")
		int countForUserName = jdbcTemplate.queryForInt(sql1, value1);
        if (countForUserName > 0) {
			throw new OspDaoException("UserName already exists. Please use different one.");
			
        } else {
            String sql2 = "select count(*) from user_login  where contact  = ? ";
            Object[] value2 = new Object[]{usrBean.getContactNumber()};
            @SuppressWarnings("deprecation")
			int countForContact = jdbcTemplate.queryForInt(sql2, value2);
            if (countForContact > 0) {
            	throw new OspDaoException("Contact Number already exists. Please use forgot username/pass to retrieve account details if not able to login.");
            } else {
                String sql3 = "select count(*) from user_login  where email    = ? ";
                Object[] value3 = new Object[]{usrBean.getEmail()};
                @SuppressWarnings("deprecation")
				int countForEmail = jdbcTemplate.queryForInt(sql3, value3);
                if (countForEmail > 0) {
                	throw new OspDaoException("Email id already exists. Please use forgot username/pass to retrieve account details if not able to login.");
                } else {
                    return "valid";
                }
            }
        }

    }

    public void createNewUser(UserBean user)throws RuntimeException{
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

    }

    public void emailUpdateStatus(UserBean user,AccessToken access)throws OspDaoException {
        String updateEmailStatusSql =" UPDATE access_token acc JOIN user_login ul on ul.id = acc.userid"
                + "  SET active_indicator = ? ,EMAILVERIFIED = ?"
                + " where ul.username = ? and acc.uuid = ?";
        int count = jdbcTemplate.update(updateEmailStatusSql, new Object[]{access.getActiveIndicator(),user.getEmailVerified(), user.getUserName(), user.getUUID()});
        if (count != 1) {
        	throw new OspDaoException();
        } 
    }
      public void smsUpdateStatus(UserBean user,AccessToken access)throws OspDaoException {
        String updateSmsStatusSql =" UPDATE access_token acc JOIN user_login ul on ul.id = acc.userid"
                + "  SET active_indicator = ? ,SMSVERIFIED = ?"
                + " where ul.username = ? and acc.uuid = ?";
        int count = jdbcTemplate.update(updateSmsStatusSql, new Object[]{access.getActiveIndicator(),user.getEmailVerified(), user.getUserName(), user.getUUID()});
        if (count != 1) {
        	throw new OspDaoException();
        } 
    }
        public void FUPUpdateStatus(UserBean user,AccessToken access)throws OspDaoException{
        String updateEmailStatusSql =" UPDATE access_token acc JOIN user_login ul on ul.id = acc.userid"
                + "  SET active_indicator = ? "
                + " where ul.username = ? and acc.uuid = ?";
        int count = jdbcTemplate.update(updateEmailStatusSql, new Object[]{access.getActiveIndicator(),user.getEmailVerified(), user.getUserName(), user.getUUID()});
        if (count != 1) {
        	throw new OspDaoException();
        } 
    }
/*
 * 
 */

    public int getUserLinkValidCheck(UserBean user, AccessToken access) {
        String getUserSql = "select count(1) from user_login ul JOIN access_token "
                + " acc on acc.userid= ul.id where ul.username = ? and "
                + " acc.uuid=? and"
                + " acc.active_indicator=? and"
                + " acc.type=? and"
                + " acc.expire_time < ?";

        Object[] value1 = new Object[]{user.getUserName(), user.getUUID(), access.getActiveIndicator(), access.getType(), access.getExpireTime()};
        int countForUserName = jdbcTemplate.queryForInt(getUserSql, value1);
        return countForUserName;
    }

}
