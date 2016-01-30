package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.Professional;

public class ProfessionalMapper implements RowMapper {

  @Override
  public List<Professional> mapRow(ResultSet rs, int arg1) throws SQLException {
    // TODO Auto-generated method stub
    List<Professional> pList = new ArrayList<Professional>();
    Professional professional = null;
    while (rs.next()) {
      professional = new Professional();
      professional.setProfessionType(rs.getString("P_TYPE"));
      professional.setProfessionalName(rs.getString("P_NAME"));
      professional.setProfessioanlId(rs.getString("P_ID"));
      pList.add(professional);
    }
    return pList;
  }

}
