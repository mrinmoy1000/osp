package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.CategoryDTO;
import com.flamingos.osp.dto.SubCatDTO;

public class SubCategoryRowMapper implements RowMapper<SubCatDTO> {

  @Override
  public SubCatDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    SubCatDTO subCatDto = null;
    if (null != rs) {
      subCatDto = new SubCatDTO();
      subCatDto.setCatId(rs.getInt("CAT_ID"));
      subCatDto.setSubCatName(rs.getString("SUB_CAT_NAME"));
      subCatDto.setDisplayName(rs.getString("DISPLAY_NAME"));
      subCatDto.setSubCatId(rs.getInt("SUB_CAT_ID"));
    }

    return subCatDto;
  }
}
