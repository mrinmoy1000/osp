package com.flamingos.osp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.ConfigLoaderDAO;
import com.flamingos.osp.dto.CatSubCatDTO;
import com.flamingos.osp.dto.ConfigParamDTO;
import com.flamingos.osp.service.ConfigParamLoaderService;

@Service
public class ConfigParamLoaderServiceImpl implements ConfigParamLoaderService {

  @Autowired
  private ConfigLoaderDAO configLoaderDao;

  @Override
  public List<ConfigParamDTO> loadConfigParam() throws Exception {
    return getConfigLoaderDao().loadConfigParam();
  }

  public ConfigLoaderDAO getConfigLoaderDao() {
    return configLoaderDao;
  }

  @Override
  public List<TemplateBean> getAllTemplate() throws Exception {
    return configLoaderDao.getAllTemplate();
  }

  @Override
  public List<RoleBean> getAllRoles() throws Exception {
    return configLoaderDao.getAllRoles();
  }

  @Override
  public List<CatSubCatDTO> getAllCategories() throws Exception {
    return configLoaderDao.getAllCategories();
  }

  @Override
  public List<CatSubCatDTO> getAllSubCategories() throws Exception {
    return configLoaderDao.getAllSubCategories();
  }
}
