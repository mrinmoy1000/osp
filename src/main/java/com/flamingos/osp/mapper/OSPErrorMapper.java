package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.OSPErrorDTO;

public class OSPErrorMapper implements RowMapper<OSPErrorDTO> {

  @Override
  public OSPErrorDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    OSPErrorDTO errorDto=null;
    if(rs!=null){
      errorDto=new OSPErrorDTO();
      errorDto.setErrorName(rs.getString("ERROR_NAME"));
      errorDto.setErrorCode(rs.getString("ERROR_CODE"));
      errorDto.setErrorDesc(rs.getString("ERROR_DESC"));
    }
    return errorDto;
  }

}
