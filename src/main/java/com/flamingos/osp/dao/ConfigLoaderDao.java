package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OspDaoException;

public interface ConfigLoaderDao {
	public List<ConfigParamDto> loadConfigParam()throws Exception;

	public List<TemplateBean> getAllTemplate() throws OspDaoException;
}
