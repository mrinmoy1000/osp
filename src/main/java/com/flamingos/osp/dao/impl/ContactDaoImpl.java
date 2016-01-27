package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ContactDao;
import com.flamingos.osp.exception.OspDaoException;

public class ContactDaoImpl implements ContactDao {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int addContact(OspProfessionalBean professionalBean) throws OspDaoException {
    Map<String, Object> contactMap = new HashMap<String, Object>();
    Number generateContactId;
    contactMap.put("CONTACT_TYPE", professionalBean.getContact().getContactType());
    contactMap.put("CONTACT_PHONE", professionalBean.getContact().getContactPhone());
    contactMap.put("CONTACT_EMAIL", professionalBean.getContact().getContactEmail());
    contactMap.put("ACTIVE_STATUS", professionalBean.getContact().getActiveStatus());
    contactMap.put("CREATED_TS", new Timestamp(new Date().getTime()));
    contactMap.put("CREATED_BY", professionalBean.getContact().getCreatedBy());
    try {
      SimpleJdbcInsert simpleInsert =
          new SimpleJdbcInsert(jdbcTemplate).withTableName("OSP_CONTACT").usingGeneratedKeyColumns(
              "CONTACT_ID");
      generateContactId = simpleInsert.executeAndReturnKey(contactMap);
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }
    return generateContactId.intValue();
  }

}
