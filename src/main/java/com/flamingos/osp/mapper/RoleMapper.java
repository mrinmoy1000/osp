/**
 * 
 */
package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.bean.RoleBean;

/**
 * @author Mrinmoy
 *
 */
public class RoleMapper implements RowMapper<RoleBean> {

  @Override
  public RoleBean mapRow(ResultSet rs, int rowNum) throws SQLException {
    RoleBean oRoleBean = new RoleBean();
    oRoleBean.setRoleId(rs.getInt("ROLE_ID"));
    oRoleBean.setRoleName(rs.getString("ROLE_NAME"));
    oRoleBean.setDispalyName(rs.getString("DISPLAY_NAME"));
    oRoleBean.setActiveStatus(rs.getInt("ACTIVE_STATUS"));
    return oRoleBean;
  }

}
