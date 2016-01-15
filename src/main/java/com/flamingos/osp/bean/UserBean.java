package com.flamingos.osp.bean;

public class UserBean {
	public String getEmailUUID() {
		return emailUUID;
	}

	public void setEmailUUID(String emailUUID) {
		this.emailUUID = emailUUID;
	}

	public String getSmsUUID() {
		return smsUUID;
	}

	public void setSmsUUID(String smsUUID) {
		this.smsUUID = smsUUID;
	}

	private int id;
	private String userName;
	private String password;
	private long contactNumber;
	private String email;
	private String activeStatus;
	private String emailUUID;
	private String smsUUID;
	private String fupUUID;
	public String getFupUUID() {
		return fupUUID;
	}

	public void setFupUUID(String fupUUID) {
		this.fupUUID = fupUUID;
	}

	private String smsVerfied;
	private String emailVerified;
	private int userTypeCD;
	private int role_id;
	private String record_type;

	public String getRecord_type() {
		return record_type;
	}

	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String firstName;
	private String middleName;
	private String lastName;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the contactNumber
	 */
	public long getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the activeStatus
	 */
	public String getActiveStatus() {
		return activeStatus;
	}

	/**
	 * @param activeStatus
	 *            the activeStatus to set
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * @return the smsVerfied
	 */
	public String getSmsVerfied() {
		return smsVerfied;
	}

	/**
	 * @param smsVerfied
	 *            the smsVerfied to set
	 */
	public void setSmsVerfied(String smsVerfied) {
		this.smsVerfied = smsVerfied;
	}

	/**
	 * @return the emailVerified
	 */
	public String getEmailVerified() {
		return emailVerified;
	}

	/**
	 * @param emailVerified
	 *            the emailVerified to set
	 */
	public void setEmailVerified(String emailVerified) {
		this.emailVerified = emailVerified;
	}

	/**
	 * @return the userTypeCD
	 */
	public int getUserTypeCD() {
		return userTypeCD;
	}

	/**
	 * @param userTypeCD
	 *            the userTypeCD to set
	 */
	public void setUserTypeCD(int userTypeCD) {
		this.userTypeCD = userTypeCD;
	}


}
