package com.flamingos.osp.dao;


import com.flamingos.osp.bean.OspProfessionalBean;
import com.flamingos.osp.exception.OspDaoException;

public interface AddressDao {

  public void saveAddress(OspProfessionalBean professionalBean) throws OspDaoException;

}
