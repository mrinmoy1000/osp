package com.flamingos.osp.dto;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class CommonParamDto implements Serializable {

  private static final Logger logger = Logger.getLogger(CommonParamDto.class);
  private static final long serialVersionUID = 1L;

  private int id;
  private int name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getName() {
    return name;
  }

  public void setName(int name) {
    this.name = name;
  }
}
