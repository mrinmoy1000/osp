package com.flamingos.osp.service;

import java.util.List;

import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OspServiceException;

public interface ConfigParamLoaderService {

	public List<ConfigParamDto> loadConfigParam() throws Exception;

	public List<TemplateBean> getAllTemplate() throws Exception;
}
