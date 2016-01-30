package com.flamingos.osp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserRegistrationDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String segment;
  private List<String> category;
  private List<String> subCategory;
  private String name;
  private String mName;
  private String sName;
  private String contactNo;
  private String pContactNo;
  private String sContactNo;
  private Date dob;
  private String gender;
  private String maritialStatus;
  private Date anniversaryDate;
  private String nationality;
  private String itPAN;
  private ResAddressDTO resAddress;
  private List<String> specializations;
  private String regNo;
  private String regAuthority;
  private List<UserQualificationDTO> qualifications;
  private List<UserExperienceDTO> experiences;
  private List<UserAcheivementDTO> acheivements;
  private List<UserMemberShipDTO> memberships;
  private List<String> publications;
  private List<String> presentations;
  private List<UserAvailChamber> chambers;
  private UserAvailAuthority authority;

  public String getSegment() {
    return segment;
  }

  public void setSegment(String segment) {
    this.segment = segment;
  }

  public List<String> getCategory() {
    return category;
  }

  public void setCategory(List<String> category) {
    this.category = category;
  }

  public List<String> getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(List<String> subCategory) {
    this.subCategory = subCategory;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getmName() {
    return mName;
  }

  public void setmName(String mName) {
    this.mName = mName;
  }

  public String getsName() {
    return sName;
  }

  public void setsName(String sName) {
    this.sName = sName;
  }

  public String getContactNo() {
    return contactNo;
  }

  public void setContactNo(String contactNo) {
    this.contactNo = contactNo;
  }

  public String getpContactNo() {
    return pContactNo;
  }

  public void setpContactNo(String pContactNo) {
    this.pContactNo = pContactNo;
  }

  public String getsContactNo() {
    return sContactNo;
  }

  public void setsContactNo(String sContactNo) {
    this.sContactNo = sContactNo;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getMaritialStatus() {
    return maritialStatus;
  }

  public void setMaritialStatus(String maritialStatus) {
    this.maritialStatus = maritialStatus;
  }

  public Date getAnniversaryDate() {
    return anniversaryDate;
  }

  public void setAnniversaryDate(Date anniversaryDate) {
    this.anniversaryDate = anniversaryDate;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public String getItPAN() {
    return itPAN;
  }

  public void setItPAN(String itPAN) {
    this.itPAN = itPAN;
  }

  public ResAddressDTO getResAddress() {
    return resAddress;
  }

  public void setResAddress(ResAddressDTO resAddress) {
    this.resAddress = resAddress;
  }

  public List<String> getSpecializations() {
    return specializations;
  }

  public void setSpecializations(List<String> specializations) {
    this.specializations = specializations;
  }

  public String getRegNo() {
    return regNo;
  }

  public void setRegNo(String regNo) {
    this.regNo = regNo;
  }

  public String getRegAuthority() {
    return regAuthority;
  }

  public void setRegAuthority(String regAuthority) {
    this.regAuthority = regAuthority;
  }

  public List<UserQualificationDTO> getQualifications() {
    return qualifications;
  }

  public void setQualifications(List<UserQualificationDTO> qualifications) {
    this.qualifications = qualifications;
  }

  public List<UserExperienceDTO> getExperiences() {
    return experiences;
  }

  public void setExperiences(List<UserExperienceDTO> experiences) {
    this.experiences = experiences;
  }

  public List<UserAcheivementDTO> getAcheivements() {
    return acheivements;
  }

  public void setAcheivements(List<UserAcheivementDTO> acheivements) {
    this.acheivements = acheivements;
  }

  public List<UserMemberShipDTO> getMemberships() {
    return memberships;
  }

  public void setMemberships(List<UserMemberShipDTO> memberships) {
    this.memberships = memberships;
  }

  public List<String> getPublications() {
    return publications;
  }

  public void setPublications(List<String> publications) {
    this.publications = publications;
  }

  public List<String> getPresentations() {
    return presentations;
  }

  public void setPresentations(List<String> presentations) {
    this.presentations = presentations;
  }

  public List<UserAvailChamber> getChambers() {
    return chambers;
  }

  public void setChambers(List<UserAvailChamber> chambers) {
    this.chambers = chambers;
  }

  public UserAvailAuthority getAuthority() {
    return authority;
  }

  public void setAuthority(UserAvailAuthority authority) {
    this.authority = authority;
  }



}
