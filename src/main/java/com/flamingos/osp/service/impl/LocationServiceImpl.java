package com.flamingos.osp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flamingos.osp.dao.LocationDAO;
import com.flamingos.osp.dto.LocationDTO;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

  @Autowired
  LocationDAO locationDao;

  @Override
  public List<LocationDTO> getLocationList() throws OSPBusinessException {
    return locationDao.getLocationList();
  }

}
