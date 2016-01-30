package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ContactDao;
import com.flamingos.osp.exception.OspDaoException;
@Repository
public class ContactDaoImpl implements ContactDao {
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public void saveContact(OspProfessionalBean professionalBean) throws OspDaoException {
    String sql =
        "INSERT INTO OSP_CONTACT(CONTACT_TYPE,CONTACT_PHONE,CONTACT_EMAIL,ACTIVE_STATUS,CREATED_TS,CREATED_BY) VALUES(:CONTACT_TYPE, :CONTACT_PHONE, :CONTACT_EMAIL, :ACTIVE_STATUS, :CREATED_TS, :CREATED_BY)";
    try {
      GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
      MapSqlParameterSource namedParameters = new MapSqlParameterSource();
      namedParameters.addValue("CONTACT_TYPE", professionalBean.getContact().getContactType());
      namedParameters.addValue("CONTACT_PHONE", professionalBean.getContact().getContactPhone());
      namedParameters.addValue("CONTACT_EMAIL", professionalBean.getContact().getContactEmail());
      namedParameters.addValue("ACTIVE_STATUS", professionalBean.getContact().getActiveStatus());
      namedParameters.addValue("CREATED_TS", new Timestamp(new Date().getTime()));
      namedParameters.addValue("CREATED_BY", professionalBean.getContact().getCreatedBy());      

      namedJdbcTemplate.update(sql, namedParameters, generatedKeyHolder);

      professionalBean.getContact().setContactId(generatedKeyHolder.getKey().intValue());
      
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }    
  }

}
