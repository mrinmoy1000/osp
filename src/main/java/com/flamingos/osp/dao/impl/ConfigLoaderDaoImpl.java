package com.flamingos.osp.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.flamingos.osp.dao.ConfigLoaderDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.mapper.ConfigParamRowMapper;

public class ConfigLoaderDaoImpl extends BaseDaoImpl implements ConfigLoaderDao{
	
	@Value("${query_osp_parameter_select}")
	private String QUERY_OSP_PARAMETER_SELECT;

	@Override
	public List<ConfigParamDto> loadConfigParam() throws Exception {
		List<ConfigParamDto> paramList=new ArrayList<ConfigParamDto>();
		Object[] values=new Object[]{1};
		paramList=getJdbcTemplate().query(QUERY_OSP_PARAMETER_SELECT, values, new ConfigParamRowMapper());
		return paramList;
	}

}