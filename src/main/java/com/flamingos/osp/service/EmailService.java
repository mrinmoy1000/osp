package com.flamingos.osp.service;

import com.flamingos.osp.exception.OspServiceException;

public interface EmailService {
  public void sendMail(String templateName, String emailId, String content,String subject,String addresseeName) throws OspServiceException;
}
