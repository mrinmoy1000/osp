package com.flamingos.osp.dto;

import java.sql.Timestamp;

public class UserDTO {

  // User_Name
  private String userName;

  private String userPass;

  private String userContact;

  private String email;

  // Role_id
  private long userId;

  private String userType;

  private String emailVerified;

  private String smsVerified;

  private int numberOfAttempts;

  private String name;

  private String roleName;

  private int roleId;

  private String activationStatus;

  private Timestamp passwordExpiryTS;

  private String returnStatus;

  private String returnMessage;

  private int typeId;

  private String paramName;

  public String getParamName() {
    return paramName;
  }

  public void setParamName(String paramName) {
    this.paramName = paramName;
  }

  public String getReturnStatus() {
    return returnStatus;
  }

  public void setReturnStatus(String returnStatus) {
    this.returnStatus = returnStatus;
  }

  public String getReturnMessage() {
    return returnMessage;
  }

  public void setReturnMessage(String returnMessage) {
    this.returnMessage = returnMessage;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getEmailVerified() {
    return emailVerified;
  }

  public void setEmailVerified(String emailVerified) {
    this.emailVerified = emailVerified;
  }

  public String getSmsVerified() {
    return smsVerified;
  }

  public void setSmsVerified(String smsVerified) {
    this.smsVerified = smsVerified;
  }

  public int getNumberOfAttempts() {
    return numberOfAttempts;
  }

  public void setNumberOfAttempts(int numberOfAttempts) {
    this.numberOfAttempts = numberOfAttempts;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public int getRoleId() {
    return roleId;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  public String getActivationStatus() {
    return activationStatus;
  }

  public void setActivationStatus(String activationStatus) {
    this.activationStatus = activationStatus;
  }

  public Timestamp getPasswordExpiryTS() {
    return passwordExpiryTS;
  }

  public void setPasswordExpiryTS(Timestamp passwordExpiryTS) {
    this.passwordExpiryTS = passwordExpiryTS;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getUserPass() {
    return userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = userPass;
  }

  public String getUserContact() {
    return userContact;
  }

  public void setUserContact(String userContact) {
    this.userContact = userContact;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

}
