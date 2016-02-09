package com.flamingos.osp.service;

import java.util.List;
import com.flamingos.osp.dto.ConfigParamDTO;

public interface ConfigParamLoaderService {

  public List<ConfigParamDTO> loadConfigParam() throws Exception;

}
