package com.flamingos.osp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flamingos.osp.dao.LocationDao;
import com.flamingos.osp.dto.LocationDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService{
  
  @Autowired
  LocationDao countryDao;

  @Override
  public List<LocationDto> getLocationList() throws OSPBusinessException {
    try{
      countryDao.getLocationList();
    }catch(Exception ex){
      throw new OSPBusinessException("","","", ex);
    }
    return null;
  }

}
