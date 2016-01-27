package com.flamingos.osp.dao.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.dao.AddressDao;
import com.flamingos.osp.exception.OspDaoException;

public class AddressDaoImpl implements AddressDao {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int addAddress(OspProfessionalBean professionalBean) throws OspDaoException {
    Map<String, Object> addressMap = new HashMap<String, Object>();
    Number generateProfKey;
    addressMap.put("LOCATION_ID", professionalBean.getAddress().getLocationId());
    addressMap.put("OTHER_AREA", professionalBean.getAddress().getOtherArea());
    addressMap.put("LINE_1", professionalBean.getAddress().getLine1());
    addressMap.put("LINE_2", professionalBean.getAddress().getLine2());
    addressMap.put("ACTIVE_STATUS", professionalBean.getAddress().getActiveStatus());
    addressMap.put("CREATED_TS", new Timestamp(new Date().getTime()));
    addressMap.put("CREATED_BY", professionalBean.getAddress().getCreatedBy());
    try {

      SimpleJdbcInsert simpleInsert =
          new SimpleJdbcInsert(jdbcTemplate).withTableName("OSP_ADDRESS").usingGeneratedKeyColumns(
              "ADDRESS_ID");
      generateProfKey = simpleInsert.executeAndReturnKey(addressMap);
    } catch (RuntimeException exp) {
      throw new OspDaoException(exp);
    }

    return generateProfKey.intValue();
  }

}
