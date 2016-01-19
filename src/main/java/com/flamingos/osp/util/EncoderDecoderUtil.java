/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flamingos.osp.util;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 *
 * @author developerPro
 */
@Component
public class EncoderDecoderUtil {

	public String getEncodedValue(String decryptData) {
		final String s = decryptData;
		final byte[] authBytes = s.getBytes(StandardCharsets.UTF_8);
		final String encoded = Base64.getEncoder().encodeToString(authBytes);
		return encoded;

	}

	public String getDecodedValue(String encryptData) {

		final String s = encryptData;

		final byte[] authBytes = Base64.getDecoder().decode(s);
		final String decoded = new String(authBytes);

		return decoded;
	}

	public static void main(String... args) throws Exception {
		// encoderDecoder ec = new encoderDecoder();
		// System.out.println("encoded = " + ec.getEncodedValue("jeet128o094"));
		// System.out.println("decoded =  " +
		// ec.getDecodedValue("cHJvZlVzZXIy"));
		Date dNow = new Date(); // Instantiate a Date object
		Calendar cal = Calendar.getInstance();
		// System.out.print(cal.getTime());

		System.out.println(new Timestamp(new Date().getTime()));
	}
}
