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
    oRoleBean.setRoleId(rs.getInt("role_id"));
    oRoleBean.setRoleName(rs.getString("role_name"));
    oRoleBean.setDispalyName(rs.getString("dispaly_name"));
    oRoleBean.setActiveStatus(rs.getInt("active_status"));
    return oRoleBean;
  }

}
