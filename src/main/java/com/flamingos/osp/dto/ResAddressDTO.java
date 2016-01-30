package com.flamingos.osp.dto;

import java.io.Serializable;

public class ResAddressDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  private String addrLine1;
  private String addrLine2;
  private String town;
  private String village;
  private String district;
  private String state;
  private String pinCode;
  private String resContactNo1;
  private String resContactNo2;

  public String getAddrLine1() {
    return addrLine1;
  }

  public void setAddrLine1(String addrLine1) {
    this.addrLine1 = addrLine1;
  }

  public String getAddrLine2() {
    return addrLine2;
  }

  public void setAddrLine2(String addrLine2) {
    this.addrLine2 = addrLine2;
  }

  public String getTown() {
    return town;
  }

  public void setTown(String town) {
    this.town = town;
  }

  public String getVillage() {
    return village;
  }

  public void setVillage(String village) {
    this.village = village;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPinCode() {
    return pinCode;
  }

  public void setPinCode(String pinCode) {
    this.pinCode = pinCode;
  }

  public String getResContactNo1() {
    return resContactNo1;
  }

  public void setResContactNo1(String resContactNo1) {
    this.resContactNo1 = resContactNo1;
  }

  public String getResContactNo2() {
    return resContactNo2;
  }

  public void setResContactNo2(String resContactNo2) {
    this.resContactNo2 = resContactNo2;
  }


}
