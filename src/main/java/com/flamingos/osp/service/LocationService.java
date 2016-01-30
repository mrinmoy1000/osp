package com.flamingos.osp.service;

import java.util.List;

import com.flamingos.osp.dto.LocationDto;
import com.flamingos.osp.exception.OSPBusinessException;

public interface LocationService {
  public List<LocationDto> getLocationList() throws OSPBusinessException;

}
