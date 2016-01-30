package com.flamingos.osp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.flamingos.osp.dto.Professional;
import com.flamingos.osp.mapper.ProfessionalMapper;

@Transactional(propagation = Propagation.REQUIRED)
public class TicketDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @SuppressWarnings("unchecked")
  public List<Professional> fetchAllProfessional(String professionType) {
    String sql = "select * from professional where p_type=?";
    Object[] values = new Object[] {professionType};
    List<Professional> pList = jdbcTemplate.query(sql, values, new ProfessionalMapper());
    return pList;

  }

}
