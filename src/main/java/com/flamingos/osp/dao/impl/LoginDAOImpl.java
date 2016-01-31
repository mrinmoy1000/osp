package com.flamingos.osp.dao.impl;

import com.flamingos.osp.bean.ConfigParamBean;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flamingos.osp.dao.LoginDAO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.dto.UserDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import org.apache.log4j.Logger;

@Repository
public class LoginDAOImpl implements LoginDAO {
private static final Logger logger = Logger.getLogger(LoginDAOImpl.class);
    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    @Autowired
    private ConfigParamBean configParamBean;

    private final String loginCheckSql = "select  * from OSP_USER_CREDENTIAL where " + AppConstants.USER_NAME + " = :username";

    @Override
    public UserDTO getUser(UserBean loginBean) throws OspDaoException {
       logger.debug("Entrying LoginDao >> getUser() method");
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("username", loginBean.getUserName());

    try {

            return namedJdbcTemplate.queryForObject(loginCheckSql, paramMap, new RowMapper<UserDTO>() {
                @Override
                public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    UserDTO user = new UserDTO();
                    user.setUserId(rs.getLong(AppConstants.RECORD_ID));
                    user.setUserPass(rs.getString(AppConstants.PASSWORD));
                    user.setUserName(rs.getString(AppConstants.USER_NAME));
                    user.setUserContact(rs.getString(AppConstants.CONTACT_NUMBER));
                    user.setEmail(rs.getString(AppConstants.EMAIL));
                    user.setActivationStatus(rs.getString(AppConstants.ACTIVATION_STATUS));
                    return user;
                }
            });

        } catch (EmptyResultDataAccessException e) {
            logger.error("no record found . Explicitly throwing exception",e);
            return null;
        }finally{
        logger.debug("Exiting LoginDao << getUser() method");
        }
    }

    @Override
    public UserDTO checkForUser(UserBean usrBean) throws OSPBusinessException {
            logger.debug("entrying LoginDao getUser method");
        String userNameSql = "SELECT * FROM OSP_USER_CREDENTIAL WHERE  " + AppConstants.USER_NAME + "=:USERNAME and " + AppConstants.EMAIL + "=:EMAIl";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("USERNAME", usrBean.getUserName());
        paramMap.put("EMAIl", usrBean.getEmail());

    try {

            return namedJdbcTemplate.queryForObject(userNameSql, paramMap,
                    new RowMapper<UserDTO>() {
                        @Override
                        public UserDTO mapRow(ResultSet rs, int rowNum)
                        throws SQLException {
                            UserDTO user = new UserDTO();
                            user.setUserId(rs.getLong(AppConstants.RECORD_ID));
                            user.setUserName(rs.getString(AppConstants.USER_NAME));
                            user.setUserContact(rs.getString(AppConstants.CONTACT_NUMBER));
                            user.setEmail(rs.getString(AppConstants.EMAIL));
                            user.setActivationStatus(rs.getString(AppConstants.ACTIVATION_STATUS));

                            return user;
                        }
                    });

         } catch (EmptyResultDataAccessException e) {
            logger.error("no record found . Explicitly throwing exception",e);
            return null;
        }finally{
        logger.debug("Exiting LoginDao << checkUser() method");
        }
    }

    @Override
    public int addFUPAccessToken(UserBean user, int fupExpireTime) throws OSPBusinessException {
    	      logger.debug("Entrying LoginDao >> addFUPAccessToken() method");
        ConfigParamDTO oParamFUPChannel =
    	          configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
    	              AppConstants.PARAM_NAME_FUP);
            String insertAccessToken = "INSERT INTO OSP_ACCESS_TOKEN VALUES "
                    + "(:" + AppConstants.USER_ID + ","
                    + ":" + AppConstants.TYPE + ","
                    + ":" + AppConstants.UUID + ","
                    + ":" + AppConstants.TOKEN_EXPIRY_DT + ","
                    + ":" + AppConstants.IS_USED + ","
                    + ":" + AppConstants.CREATED_TS + ","
                    + ":" + AppConstants.UPDATED_TS + ","
                    + ":" + AppConstants.CREATED_BY + ","
                    + ":" + AppConstants.UPDATED_BY + ")";
            Map<String, Object> accessTokenMapforFUP = new HashMap<String, Object>();
            accessTokenMapforFUP.put(AppConstants.USER_ID, user.getUser_id());
            accessTokenMapforFUP.put(AppConstants.TYPE, oParamFUPChannel.getParameterid());
            accessTokenMapforFUP.put(AppConstants.UUID, user.getFupUUID());
            accessTokenMapforFUP.put(AppConstants.TOKEN_EXPIRY_DT, new Timestamp(new Date().getTime() + (1 * fupExpireTime * 60 * 60 * 1000)));
            accessTokenMapforFUP.put(AppConstants.IS_USED, 0);
            accessTokenMapforFUP.put(AppConstants.CREATED_TS, new Timestamp(new Date().getTime()));
            accessTokenMapforFUP.put(AppConstants.UPDATE_TS, null);
            accessTokenMapforFUP.put(AppConstants.CREATED_BY, user.getUserName());
            accessTokenMapforFUP.put(AppConstants.UPDATE_BY, null);
            int count = namedJdbcTemplate.update(insertAccessToken, accessTokenMapforFUP);
            logger.debug("Exiting LoginDao << addFUPAccessToken() method");
            return count;
      
    }

  }
