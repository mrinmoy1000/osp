package com.flamingos.osp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.ConfigLoaderDAO;
import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.mapper.CategoryRowMapper;
import com.flamingos.osp.mapper.ConfigParamRowMapper;
import com.flamingos.osp.mapper.RoleMapper;
import com.flamingos.osp.mapper.TemplateRowMapper;
import com.flamingos.osp.util.AppConstants;

@Repository
public class ConfigLoaderDAOImpl extends BaseDAOImpl implements ConfigLoaderDAO {

  @Value("${query_osp_parameter_select}")
  private String QUERY_OSP_PARAMETER_SELECT;
  @Value("${query_osp_template_select}")
  private String QUERY_OSP_TEMPLATE_SELECT;
  @Value("${query_osp_category_select}")
  private String QUERY_OSP_CATEGORY_SELECT;
  @Value("${query_osp_subcategory_select}")
  private String QUERY_OSP_SUBCATEGORY_SELECT;
  @Value("${query_osp_role_select}")
  private String QUERY_OSP_ROLE_SELECT;

  @Override
  public List<ConfigParamDTO> loadConfigParam() throws OSPBusinessException, Exception {
    List<ConfigParamDTO> paramList = null;
    Object[] values = new Object[] {1};
    paramList =
        getJdbcTemplate().query(QUERY_OSP_PARAMETER_SELECT, values, new ConfigParamRowMapper());
    if (null == paramList) {
      throw new OSPBusinessException(AppConstants.CONFIG_LOADING_MODULE,
          AppConstants.DB_NO_RECORD_FOUND_ERRCODE, AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return paramList;
  }
}
