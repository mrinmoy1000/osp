package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.flamingos.osp.dto.CategoryDTO;

public class CategoryRowMapper implements RowMapper<CategoryDTO> {

  @Override
  public CategoryDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CategoryDTO categoryDto = null;
    if (null != rs) {
      categoryDto = new CategoryDTO();
      categoryDto.setCatId(rs.getInt("CAT_ID"));
      categoryDto.setCatName(rs.getString("CAT_NAME"));
      categoryDto.setDisplayName(rs.getString("DISPLAY_NAME"));
    }
    return categoryDto;
  }
}
