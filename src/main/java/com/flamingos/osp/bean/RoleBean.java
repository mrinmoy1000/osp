/**
 * 
 */
package com.flamingos.osp.bean;

/**
 * @author Mrinmoy
 *
 */
public class RoleBean {
  private int roleId;
  private String roleName;
  private String dispalyName;
  private int activeStatus;

  /**
   * @return the roleId
   */
  public int getRoleId() {
    return roleId;
  }

  /**
   * @param roleId the roleId to set
   */
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  /**
   * @return the roleName
   */
  public String getRoleName() {
    return roleName;
  }

  /**
   * @param roleName the roleName to set
   */
  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  /**
   * @return the dispalyName
   */
  public String getDispalyName() {
    return dispalyName;
  }

  /**
   * @param dispalyName the dispalyName to set
   */
  public void setDispalyName(String dispalyName) {
    this.dispalyName = dispalyName;
  }

  /**
   * @return the activeStatus
   */
  public int getActiveStatus() {
    return activeStatus;
  }

  /**
   * @param activeStatus the activeStatus to set
   */
  public void setActiveStatus(int activeStatus) {
    this.activeStatus = activeStatus;
  }
}