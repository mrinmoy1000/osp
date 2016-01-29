package com.flamingos.osp.util;

public class AppConstants {

  public static String LOG_TYPE_INFO = "INFO";
  public static String LOG_TYPE_DEBUG = "DEBUG";
  public static String LOG_TYPE_ERROR = "ERROR";
  public static String PIPE_SEPARATOR = "||";

  public static String CONFIG_LOADING_MODULE = "CONFIG_LOADING_MODULE";
  public static String EMAIL_SENDING_MODULE = "EMAIL_MODULE";
  public static String SMS_SENDING_MODULE = "SMS_MODULE";
  public static String SIGN_UP_MODULE = "SIGN_UP_MODULE";
  public static String LOGIN_MODULE = "LOGIN_MODULE";
  public static String VERIFICATION_MODULE = "VERFICATION_MODULE";
  public static String PASSWORD_CHANGE_MODULE = "VERFICATION_MODULE";
public static String PROFESSIONAL_ADD_PROFILE_MODULE = "PROFESSIONAL_ADD_PROFILE_MODULE";
  public static String DB_NO_RECORD_FOUND_ERRCODE = "DB001";
  public static String DB_NO_RECORD_FOUND_ERRMSG = "No record found from Database";
  public static String EMAIL_EXCEPTION_ERRCODE = "EMAIL001";
  public static String EMAIL_EXCEPTION_ERRDESC = "Email Sending Falied";
  public static String SMS_EXCEPTION_ERRCODE = "SMS001";
  public static String SMS_EXCEPTION_ERRDESC = "SMS Sending Falied";
  public static String SIGN_UP_EXCEPTION_ERRCODE = "SIGNUP001";
  public static String SIGN_UP_EXCEPTION_ERRDESC = "Sign up failed";
  public static String LOGIN_EXCEPTION_ERRCODE = "LGN001";
  public static String LOGIN_EXCEPTION_ERRDESC = "Login failed";
  public static String FUP_TOKEN__ERRCODE = "FUPT001";
  public static String FUP_TOKEN_VERIFY_ERRCODE = "FUPTV001";
  public static String FUP_TOKEN_ERRDESC = "Failed to generate token for FUP";
  public static String CHANGE_PASSWORD_ERRCODE = "CHGP001";
  public static String CHANGE_PASSWORD_ERRDESC = "Failed to update password";
  public static String PROFESSIONAL_ADD_PROFILE_EXCEPTION_ERRCODE = "PROFILE001";
  public static String PROFESSIONAL_ADD_PROFILE_EXCEPTION_ERRDESC = "Professional Add Profile Failed";

  public static String DUPLICATE_USER_ERRCODE = "DUERR001";
  public static String DUPLICATE_USER_ERRDESC = "UserName already exists. Please use different one";
  
  public static String DUPLICATE_EMAIL_ERRCODE = "DEERR001";
  public static String DUPLICATE_EMAIL_ERRDESC = "Email id already exists. Please use forgot username/pass to retrieve account details if not able to login.";
  
  public static String DUPLICATE_CONTACT_ERRCODE = "DCERR001";
  public static String DUPLICATE_CONTACT_ERRDESC = "Contact Number already exists. Please use forgot username/pass to retrieve account details if not able to login.";
  
  public static String PARAM_CODE_USER_STATUS = "USER_STATUS";
  public static String PARAM_CODE_USER_TYPE = "USER_TYPE";
  public static String PARAM_NAME_PROFESSIONAL = "PREFESSIONAL";
  public static String PARAM_NAME_CLIENT = "CLIENT";
  public static String PARAM_NAME_ALL = "ALL";
  public static String PARAM_CODE_COMM_CHANNEL = "COMM_CHANNEL";
  public static String PARAM_NAME_EMAIL = "EMAIL";
  public static String PARAM_NAME_SMS = "SMS";
  public static String PARAM_NAME_MAIL = "MAIL";
  public static String PARAM_CODE_JOB_STATUS = "JOB_STATUS";
  public static String PARAM_NAME_INITIAL = "INITIAL";
  public static String PARAM_NAME_FAILES = "FAILED";
  public static String PARAM_NAME_PROCESSED = "PROCESSED";
  public static String PARAM_NAME_FUP = "FUP";
  

  
  
  /*ROLE */
  public static String ROLE_ADMINISTRATOR="ADMINISTRATOR";
  public static String ROLE_PROFESSIONAL="PROFESSIONAL";
  public static String ROLE_CLIENT="CLIENT";
  
  public static String VTEMP_QUALIFIER = "newMessage";


    public static final String SUCCESS = "success";
    public static final String FAILURE = "fail";
	public static final String ERROR = "error";
	public static final String VALID = "valid";
	public static final String INVALID = "invalid";
	public static final String INVALID_LINK = "invalid link";
	public static final String LINK_VERFIED = "y";
	public static final String LINK_NOT_VERFIED = "n";
	public static final String EMAIL_TYPE = "email";
	public static final String SMS_TYPE = "sms";
	public static final String LOGIN_SUCCESS = "login success";
	public static final String LOGIN_FAILURE = "login success";
	public static final String USER_NOT_FOUND = "user not found";
	public static final String VERIFICATION_LINK_NOTIFICATION = "A verification link send to your mail please, check your email";
	public static final String LINK_VERFIED_MESSAGE = "You have been Verified";
	public static final String CHANGE_PASSWORD_MESSAGE = "Password changed successfully";
        public static final String TOKEN_GENERATED = "new token generated sucessfully";
	public static final String TOKEN_GENERATED_FAIL = "new token generated sucessfully";
  
        
        public static final String USER_NAME = "USER_NAME";
	public static final String RECORD_ID = "RECORD_ID";	
	public static final String ROLE_ID = "ROLE_ID";
	public static final String RECORD_TYPE = "RECORD_TYPE";
	public static final String PASSWORD = "USER_CRED";
	public static final String SALT = "salt";
	public static final String CARD_EXPIRY_DATE = "CRED_EXPIRY_DATE";
	public static final String NO_OF_ATTEMPTS = "NO_OF_ATTEMPTS";
	public static final String EMAIL_VERIFIED = "EMAIL_VERYFIED";
	public static final String SMS_VERIFIED = "SMS_VERIFIED";
	public static final String ACTIVATION_STATUS = "ACTIVATION_STATUS";
	public static final String CREATED_BY = "CREATED_BY";
	public static final String CREATED_TS = "CREATED_TS";
	public static final String UPDATE_BY = "UPDATED_BY";
	public static final String UPDATE_TS = "UPDATED_TS";
	public static final String CONTACT_NUMBER = "USER_REG_PHONE";
	public static final String EMAIL = "USER_REG_EMAIL";
	public static final String FIRST_NAME = "USER_FIRST_NAME";
	public static final String MIDDLE_NAME = "USER_MIDDLE_NAME";
	public static final String LAST_NAME = "USER_LAST_NAME";
	public static final String LOGIN_TS = "LAST_LOGIN_TS";
	
	
	public static final String USER_ID = "USER_ID";
	public static final String TYPE = "TYPE";
	public static final String UUID = "UUID";
	public static final String TOKEN_EXPIRY_DT = "EXPIRY_DT";
	public static final String IS_USED = "IS_USED";
	public static final String TOKEN_CREATED_TS = "CREATED_TS";
	public static final String UPDATED_TS = "UPDATED_TS";
	public static final String TOKEN_CREATED_BY = "CREATED_BY";
	public static final String UPDATED_BY = "UPDATED_BY";
	
	public static final String PARAM_ID = "PARAM_ID";
	public static final String PARAM_VALUE = "PARAM_VALUE";
	
	public static final String USER_STATUS = "STATUS";
	public static final String PROF_ID = "PROF_ID";
}
