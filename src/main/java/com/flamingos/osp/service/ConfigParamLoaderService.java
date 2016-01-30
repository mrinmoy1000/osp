package com.flamingos.osp.service;

import java.util.List;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.ConfigParamDTO;

public interface ConfigParamLoaderService {

  public List<ConfigParamDTO> loadConfigParam() throws Exception;

  public List<TemplateBean> getAllTemplate() throws Exception;

  public List<RoleBean> getAllRoles() throws Exception;
}
