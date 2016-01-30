package com.flamingos.osp.dto;

import java.io.Serializable;

public class UserAvailAuthority implements Serializable {

  private static final long serialVersionUID = 1L;

  private String authorityName;
  private String location;
  private String remark;
  private String photograph;
  private String signature;

  public String getAuthorityName() {
    return authorityName;
  }

  public void setAuthorityName(String authorityName) {
    this.authorityName = authorityName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getPhotograph() {
    return photograph;
  }

  public void setPhotograph(String photograph) {
    this.photograph = photograph;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

}
