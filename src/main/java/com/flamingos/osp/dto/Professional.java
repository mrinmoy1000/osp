package com.flamingos.osp.dto;

public class Professional {
  private String professionType;
  private String professioanlId;
  private String professionalName;
  private String publicId;

  public String getPublicId() {
    return publicId;
  }

  public void setPublicId(String publicId) {
    this.publicId = publicId;
  }

  public String getProfessionType() {
    return professionType;
  }

  public void setProfessionType(String professionType) {
    this.professionType = professionType;
  }

  public String getProfessioanlId() {
    return professioanlId;
  }

  public void setProfessioanlId(String professioanlId) {
    this.professioanlId = professioanlId;
  }

  public String getProfessionalName() {
    return professionalName;
  }

  public void setProfessionalName(String professionalName) {
    this.professionalName = professionalName;
  }

}
