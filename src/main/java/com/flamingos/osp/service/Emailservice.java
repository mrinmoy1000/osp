package com.flamingos.osp.service;

import com.flamingos.osp.exception.OspServiceException;

public interface Emailservice {
  public void sendMail(String templateName, String emailId, String url,String subject) throws OspServiceException;
}
