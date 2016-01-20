package com.flamingos.osp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dao.ConfigLoaderDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OspDaoException;
import com.flamingos.osp.mapper.ConfigParamRowMapper;
import com.flamingos.osp.util.AppConstants;

public class ConfigLoaderDaoImpl extends BaseDaoImpl implements ConfigLoaderDao {

	@Value("${query_osp_parameter_select}")
	private String QUERY_OSP_PARAMETER_SELECT;

	@Override
	public List<ConfigParamDto> loadConfigParam() throws OSPBusinessException,
			Exception {
		List<ConfigParamDto> paramList = new ArrayList<ConfigParamDto>();
		Object[] values = new Object[] { 1 };
		paramList = getJdbcTemplate().query(QUERY_OSP_PARAMETER_SELECT, values,
				new ConfigParamRowMapper());
		if (!(paramList.size() > 0)) {
			throw new OSPBusinessException("",
					AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
					AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
		}
		return paramList;
	}

	@Override
	public List<TemplateBean> getAllTemplate() throws OspDaoException {
		// TODO Auto-generated method stub
		try {
			String sql = "select temp_name,temp_file_name from osp_comm_template";
			return getJdbcTemplate().query(sql, new RowMapper<TemplateBean>() {

				@Override
				public TemplateBean mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					// TODO Auto-generated method stub
					TemplateBean tb = new TemplateBean();
					tb.setTempName(rs.getString("temp_name"));
					tb.setTempFilePath(rs.getString("temp_name"));
					return tb;
				}

			});
		} catch (RuntimeException re) {
			throw new OspDaoException(re);
		}
	}

}
