package com.flamingos.osp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.MasterDataLoaderDAO;
import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.OSPErrorDTO;
import com.flamingos.osp.dto.SubCatDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.mapper.CategoryRowMapper;
import com.flamingos.osp.mapper.OSPErrorMapper;
import com.flamingos.osp.mapper.RoleMapper;
import com.flamingos.osp.mapper.SubCategoryRowMapper;
import com.flamingos.osp.mapper.TemplateRowMapper;
import com.flamingos.osp.util.AppConstants;

public class MasterDataLoaderDAOImpl extends BaseDAOImpl implements MasterDataLoaderDAO {

  @Value("${query_osp_template_select}")
  private String QUERY_OSP_TEMPLATE_SELECT;
  @Value("${query_osp_category_select}")
  private String QUERY_OSP_CATEGORY_SELECT;
  @Value("${query_osp_subcategory_select}")
  private String QUERY_OSP_SUBCATEGORY_SELECT;
  @Value("${query_osp_role_select}")
  private String QUERY_OSP_ROLE_SELECT;
  @Value("${query_osp_error_desc_select}")
  private String QUERY_OSP_ERROR_DESC_SELECT;

  @Override
  public List<TemplateBean> getAllTemplate() throws OSPBusinessException {
    // TODO Auto-generated method stub
    List<TemplateBean> templateList = null;

    Object[] values = new Object[] {1};
    templateList =
        getJdbcTemplate().query(QUERY_OSP_TEMPLATE_SELECT, values, new TemplateRowMapper());
    if (null == templateList) {
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
    if (null == roleList) {
      throw new OSPBusinessException("", AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
          AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return roleList;
  }

  @Override
  public List<CategoryDTO> getAllCategories() throws Exception {
    List<CategoryDTO> categoryList = null;
    Object[] values = new Object[] {1};
    categoryList =
        getJdbcTemplate().query(QUERY_OSP_CATEGORY_SELECT, values, new CategoryRowMapper());
    if (null == categoryList) {
      throw new OSPBusinessException(AppConstants.CONFIG_LOADING_MODULE,
          AppConstants.DB_NO_RECORD_FOUND_ERRCODE, AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return categoryList;
  }

  @Override
  public List<SubCatDTO> getAllSubCategories() throws Exception {
    List<SubCatDTO> subCategoryList = null;
    Object[] values = new Object[] {1};
    subCategoryList =
        getJdbcTemplate().query(QUERY_OSP_SUBCATEGORY_SELECT, values, new SubCategoryRowMapper());
    if (null == subCategoryList) {
      throw new OSPBusinessException(AppConstants.CONFIG_LOADING_MODULE,
          AppConstants.DB_NO_RECORD_FOUND_ERRCODE, AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return subCategoryList;
  }

  @Override
  public List<OSPErrorDTO> getErrorList() throws Exception {
    List<OSPErrorDTO> errorList = null;
    errorList =
        getJdbcTemplate().query(QUERY_OSP_ERROR_DESC_SELECT,new OSPErrorMapper());
    if (null == errorList) {
      throw new OSPBusinessException(AppConstants.CONFIG_LOADING_MODULE,
          AppConstants.DB_NO_RECORD_FOUND_ERRCODE, AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return errorList;
  }

}
