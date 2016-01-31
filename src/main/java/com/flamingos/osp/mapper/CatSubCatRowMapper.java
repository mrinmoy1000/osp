package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.CatSubCatDTO;
import com.flamingos.osp.util.AppConstants;

public class CatSubCatRowMapper implements RowMapper<CatSubCatDTO> {

  @Override
  public CatSubCatDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    CatSubCatDTO catSubCatDto = null;
    boolean isCatNameExists = AppConstants.FALSE;
    boolean isSubCatNameExists = AppConstants.FALSE;
    boolean isSubCatIdExists = AppConstants.FALSE;
    if (null != rs) {
      ResultSetMetaData rsmd = rs.getMetaData();
      int columns = rsmd.getColumnCount();
      for (int x = 1; x <= columns; x++) {
        if ("CAT_NAME".equals(rsmd.getColumnName(x))) {
          isCatNameExists = AppConstants.TRUE;
        }
        if ("SUB_CAT_NAME".equals(rsmd.getColumnName(x))) {
          isSubCatNameExists = AppConstants.TRUE;
        }
        if ("SUB_CAT_ID".equals(rsmd.getColumnName(x))) {
          isSubCatIdExists = AppConstants.TRUE;
        }
      }
    }
    if (null != rs) {
      catSubCatDto = new CatSubCatDTO();
      catSubCatDto.setCatId(rs.getInt("CAT_ID"));
      if (isCatNameExists) {
        catSubCatDto.setCatName(rs.getString("CAT_NAME"));
      }
      if (isSubCatNameExists) {
        catSubCatDto.setCatName(rs.getString("SUB_CAT_NAME"));
      }
      catSubCatDto.setDisplayName(rs.getString("DISPLAY_NAME"));
      if (isSubCatIdExists) {
        catSubCatDto.setSubCatId(rs.getInt("SUB_CAT_ID"));
      }
    }
    return catSubCatDto;
  }
}
