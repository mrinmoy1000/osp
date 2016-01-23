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
			template.setTemplateId(rs.getInt("comm_template_id"));
			template.setTempName(rs.getString("temp_name"));
			template.setChannelId(rs.getInt("comm_channel_id"));
			template.setTemplateCatId(rs.getInt("temp_cat_id"));
			template.setTemplateSubCatId(rs.getInt("temp_sub_cat_id"));
			template.setIsEditable(rs.getInt("temp_is_editable"));
			template.setTempFilePath(rs.getString("temp_file_path"));
		}
		return template;
	}

}
