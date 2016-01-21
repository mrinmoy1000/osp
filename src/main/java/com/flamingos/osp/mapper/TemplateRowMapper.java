package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.bean.TemplateBean;
import com.flamingos.osp.dto.ConfigParamDto;

public class TemplateRowMapper implements RowMapper<TemplateBean>{

	@Override
	public TemplateBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		TemplateBean template=null;
		if(rs!=null){
			template=new TemplateBean();
			template.setTemplateId(rs.getInt("COMM_TEMPLATE_ID"));
			template.setTempName(rs.getString("TEMP_NAME"));
			template.setChannelId(rs.getInt("COMM_CHANNEL_ID"));
			template.setTemplateCatId(rs.getInt("TEMP_CAT_ID"));
			template.setTemplateSubCatId(rs.getInt("TEMP_SUB_CAT_ID"));
			template.setIsEditable(rs.getInt("TEMP_IS_EDITABLE"));
			template.setTempFilePath(rs.getString("TEMP_FILE_PATH"));
		}
		return template;
	}

}
