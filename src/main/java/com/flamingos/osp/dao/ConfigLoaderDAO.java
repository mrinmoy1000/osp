package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.RoleBean;
import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.ConfigParamDTO;

public interface ConfigLoaderDAO {
  public List<ConfigParamDTO> loadConfigParam() throws Exception;
}
