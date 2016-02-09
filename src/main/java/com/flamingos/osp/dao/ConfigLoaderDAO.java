package com.flamingos.osp.dao;

import java.util.List;
import com.flamingos.osp.dto.ConfigParamDTO;

public interface ConfigLoaderDAO {
  public List<ConfigParamDTO> loadConfigParam() throws Exception;
}
