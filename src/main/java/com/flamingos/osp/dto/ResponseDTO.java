package com.flamingos.osp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResponseDTO {

  public String responseCode;
  public String responseMsg;
  public Object responseObj;
  public String getResponseCode() {
    return responseCode;
  }
  public void setResponseCode(String responseCode) {
    this.responseCode = responseCode;
  }
  public String getResponseMsg() {
    return responseMsg;
  }
  public void setResponseMsg(String responseMsg) {
    this.responseMsg = responseMsg;
  }
  public Object getResponseObj() {
    return responseObj;
  }
  public void setResponseObj(Object responseObj) {
    this.responseObj = responseObj;
  }

}
