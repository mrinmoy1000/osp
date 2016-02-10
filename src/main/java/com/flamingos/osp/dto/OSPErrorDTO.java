package com.flamingos.osp.dto;

public class OSPErrorDTO {
  
  private String errorCode;
  private String errorDesc;
  private String errorName;
  
  public String getErrorCode() {
    return errorCode;
  }
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }
  public String getErrorDesc() {
    return errorDesc;
  }
  public void setErrorDesc(String errorDesc) {
    this.errorDesc = errorDesc;
  }
  public String getErrorName() {
    return errorName;
  }
  public void setErrorName(String errorName) {
    this.errorName = errorName;
  }

}
