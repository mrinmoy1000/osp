package com.flamingos.osp.dto;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class CommonParamDTO implements Serializable {

  private static final Logger logger = Logger.getLogger(CommonParamDTO.class);
  private static final long serialVersionUID = 1L;

  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
