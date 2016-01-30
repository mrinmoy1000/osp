package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class LocationDto implements Serializable {

  private static final Logger logger = Logger.getLogger(OspLocationDto.class);
  private static final long serialVersionUID = 1L;

  private int locationId;
  private String locationName;
  private int locationType;
  private int locationParentId;
  @JsonIgnore
  private String locationCode;
  private List<LocationDto> childLocations;

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

  public List<LocationDto> getChildLocations() {
    return childLocations;
  }

  public void setChildLocations(List<LocationDto> childLocations) {
    this.childLocations = childLocations;
  }

  public String getLocationCode() {
    return locationCode;
  }

  public void setLocationCode(String locationCode) {
    this.locationCode = locationCode;
  }



}
