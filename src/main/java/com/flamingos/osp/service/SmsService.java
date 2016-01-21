package com.flamingos.osp.service;

import com.flamingos.osp.exception.OspServiceException;

public interface SmsService {
	public void sendSms(String sender, String content) throws OspServiceException;
}
