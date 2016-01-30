package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

public class ProfileDTO implements Serializable{
  
  private static final Logger logger = Logger.getLogger(ProfileDTO.class);
  private static final long serialVersionUID = 1L;
  
  private List<CommonParamDto> genders;
  private List<CommonParamDto> maritalStatus;
  
  private List<LocationDto> locations;

  public List<CommonParamDto> getGenders() {
    return genders;
  }

  public void setGenders(List<CommonParamDto> genders) {
    this.genders = genders;
  }

  public List<CommonParamDto> getMaritalStatus() {
    return maritalStatus;
  }

  public void setMaritalStatus(List<CommonParamDto> maritalStatus) {
    this.maritalStatus = maritalStatus;
  }

  public List<LocationDto> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationDto> locations) {
    this.locations = locations;
  }
  
  
}
