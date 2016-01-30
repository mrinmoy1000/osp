package com.flamingos.osp.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.ProfContactMapDao;
import com.flamingos.osp.exception.OspDaoException;
@Repository
public class ProfContactMapDaoImpl implements ProfContactMapDao {
  @Autowired
  private NamedParameterJdbcTemplate namedJdbcTemplate;

  @Override
  public void saveContactMap(OspProfessionalBean professionalBean) throws OspDaoException {

    String insertOspProfContactMap =
        "INSERT INTO OSP_PROF_CONTACT_MAP VALUES (:PROF_ID," + ":CONTACT_ID," + ":ACTIVE_STATUS)";

    Map<String, Object> OspProfContactMap = new HashMap<String, Object>();
    OspProfContactMap.put("PROF_ID", professionalBean.getProfId());
    OspProfContactMap.put("CONTACT_ID", professionalBean.getAddress().getAddressId());
    OspProfContactMap.put("ACTIVE_STATUS", 1);
    try {

      namedJdbcTemplate.update(insertOspProfContactMap, OspProfContactMap);
    } catch (EmptyResultDataAccessException exp) {
      throw new OspDaoException(exp);
    }
  }

}
