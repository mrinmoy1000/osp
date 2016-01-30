package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

public class LocationDto implements Serializable{
  private static final Logger logger = Logger.getLogger(OspLocationDto.class);
  private static final long serialVersionUID = 1L;
  
  private CommonParamDto gender;
  private CommonParamDto maritalStatus;
  private int locationId;
  private String locationName;  
  private int locationType;
  private int locationParentId;
  private List<OspLocationDto> childLocations;
  
  public CommonParamDto getGender() {
    return gender;
  }
  public void setGender(CommonParamDto gender) {
    this.gender = gender;
  }
  public CommonParamDto getMaritalStatus() {
    return maritalStatus;
  }
  public void setMaritalStatus(CommonParamDto maritalStatus) {
    this.maritalStatus = maritalStatus;
  }
  public int getLocationId() {
    return locationId;
  }
  public void setLocationId(int locationId) {
    this.locationId = locationId;
  }
  public String getLocationName() {
    return locationName;
  }
  public void setLocationName(String locationName) {
    this.locationName = locationName;
  }
  public int getLocationType() {
    return locationType;
  }
  public void setLocationType(int locationType) {
    this.locationType = locationType;
  }
  public int getLocationParentId() {
    return locationParentId;
  }
  public void setLocationParentId(int locationParentId) {
    this.locationParentId = locationParentId;
  }
  public List<OspLocationDto> getChildLocations() {
    return childLocations;
  }
  public void setChildLocations(List<OspLocationDto> childLocations) {
    this.childLocations = childLocations;
  }
    
}
