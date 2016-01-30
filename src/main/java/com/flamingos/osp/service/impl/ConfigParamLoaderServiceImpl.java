package com.flamingos.osp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.ConfigLoaderDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.service.ConfigParamLoaderService;

@Service
public class ConfigParamLoaderServiceImpl implements ConfigParamLoaderService {

  @Autowired
  private ConfigLoaderDao configLoaderDao;

  @Override
  public List<ConfigParamDto> loadConfigParam() throws Exception {
    return getConfigLoaderDao().loadConfigParam();
  }

  public ConfigLoaderDao getConfigLoaderDao() {
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
}
