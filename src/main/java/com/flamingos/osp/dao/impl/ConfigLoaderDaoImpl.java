package com.flamingos.osp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.ConfigLoaderDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.mapper.ConfigParamRowMapper;
import com.flamingos.osp.mapper.RoleMapper;
import com.flamingos.osp.mapper.TemplateRowMapper;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ConfigLoaderDaoImpl extends BaseDaoImpl implements ConfigLoaderDao {

  @Value("${query_osp_parameter_select}")
  private String QUERY_OSP_PARAMETER_SELECT;
  @Value("${query_osp_template_select}")
  private String QUERY_OSP_TEMPLATE_SELECT;

  @Value("${query_osp_role_select}")
  private String QUERY_OSP_ROLE_SELECT;

  @Override
  public List<ConfigParamDto> loadConfigParam() throws OSPBusinessException, Exception {
    List<ConfigParamDto> paramList = new ArrayList<ConfigParamDto>();
    Object[] values = new Object[] {1};
    paramList =
        getJdbcTemplate().query(QUERY_OSP_PARAMETER_SELECT, values, new ConfigParamRowMapper());
    if (!(paramList.size() > 0)) {
      throw new OSPBusinessException("", AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
          AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return paramList;
  }

  @Override
  public List<TemplateBean> getAllTemplate() throws OSPBusinessException {
    // TODO Auto-generated method stub
    List<TemplateBean> templateList = new ArrayList<TemplateBean>();

    Object[] values = new Object[] {1};
    templateList =
        getJdbcTemplate().query(QUERY_OSP_TEMPLATE_SELECT, values, new TemplateRowMapper());
    if (!(templateList.size() > 0)) {
      throw new OSPBusinessException("", AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
          AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return templateList;
  }

  @Override
  public List<RoleBean> getAllRoles() throws OSPBusinessException {
    // TODO Auto-generated method stub
    List<RoleBean> roleList = null;

    Object[] values = new Object[] {1};
    roleList = getJdbcTemplate().query(QUERY_OSP_ROLE_SELECT, values, new RoleMapper());
    if (!(roleList.size() > 0)) {
      throw new OSPBusinessException("", AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
          AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return roleList;
  }

}
