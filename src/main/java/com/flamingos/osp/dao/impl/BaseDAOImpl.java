package com.flamingos.osp.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDAOImpl {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private SimpleJdbcCall simpleJdbcCall;

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public SimpleJdbcCall getSimpleJdbcCall() {
    return simpleJdbcCall;
  }

  public void setSimpleJdbcCall(SimpleJdbcCall simpleJdbcCall) {
    this.simpleJdbcCall = simpleJdbcCall;
  }

}
