package com.flamingos.osp.email;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailGateway {
	 private JavaMailSender mailSender;
	  private VelocityEngine velocityEngine;

	  public void setMailSender(JavaMailSender mailSender) {
	    this.mailSender = mailSender;
	  }

	  public void setVelocityEngine(VelocityEngine velocityEngine) {
	    this.velocityEngine = velocityEngine;
	  }
	  
	  

	  public void sendMail(final Mail mail) throws MessagingException {
		  
		  MimeMessagePreparator preparator=new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage message) throws Exception {
				//MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setFrom(new InternetAddress(mail.getMailFrom()));
			   // String[] toAddresses = { new String(mail.getMailTo()) };
			    InternetAddress[] toAddresses = { new InternetAddress(mail.getMailTo()) };
			    message.setRecipients(Message.RecipientType.TO,toAddresses);;
			    message.setSubject(mail.getMailSubject());
			    message.setSentDate(new Date());
			    Template template = velocityEngine.getTemplate(mail.getTemplateName());

			    VelocityContext velocityContext = new VelocityContext();
			   // velocityContext.put("image1", "C:\\Users\\adas7\\Desktop\\BLE\\images\\test.jpg");
			   // velocityContext.put("lastName", "Chavan");
			  //  velocityContext.put("location", "Pune");

			    StringWriter stringWriter = new StringWriter();

			    template.merge(velocityContext, stringWriter);
			    
			    // creates message part
			    MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(stringWriter.toString(), "text/html");
				
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				// adds inline image attachments
				Map<String,String> mapInlineImages=mail.getInlineImages();
				if (mapInlineImages != null && mapInlineImages.size() > 0) {
					Set<String> setImageID = mapInlineImages.keySet();
					
					for (String contentId : setImageID) {
						MimeBodyPart imagePart = new MimeBodyPart();
						imagePart.setHeader("Content-ID", "<" + contentId + ">");
						imagePart.setDisposition(MimeBodyPart.INLINE);
						
						String imageFilePath = mapInlineImages.get(contentId);
						try {
							imagePart.attachFile(imageFilePath);
						} catch (IOException ex) {
							ex.printStackTrace();
						}

						multipart.addBodyPart(imagePart);
					}
				}

				message.setContent(multipart);
				
				// TODO Auto-generated method stub
				
			}
		};
		  
		  

	    

	    //message.setText(stringWriter.toString());

	    mailSender.send(preparator);
	  }

}
