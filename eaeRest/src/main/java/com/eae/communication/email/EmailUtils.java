package com.eae.communication.email;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.eclipse.persistence.internal.oxm.ByteArrayDataSource;

public class EmailUtils {
	private static final String SMTP_HOST_NAME = System.getenv("eae_email_host");
	private static final int SMTP_HOST_PORT = Integer.parseInt(System.getenv("eae_email_port"));
	private static final String SMTP_AUTH_USER = System.getenv("eae_email_user");
	private static final String SMTP_AUTH_PWD = System.getenv("eae_email_pass");
	
	private static Session buildSession() {
		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", SMTP_HOST_NAME);
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);
		return mailSession;
	}
	
	public static void sendEmail(String subject, String content, String email) {
		Session mailSession = buildSession();
		
		try {

			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(subject);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

			MimeBodyPart body = new MimeBodyPart();
			body.setText(content);

			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(body);

			message.setContent(multiPart);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

		} catch (AddressException addressException) {
			System.out.println(addressException);
		} catch (MessagingException messagingExcetion) {
			System.out.println(messagingExcetion);
		}
	}

	public static void sendInvite(String subject, String content, String email, String iCalText) {
		Session mailSession = buildSession();
		
		try {
			Transport transport = mailSession.getTransport();

			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(subject);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

			MimeBodyPart body = new MimeBodyPart();
			body.setText(content);

			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(body);

			if (iCalText != null && iCalText.length() > 0) {
				DataSource source = new ByteArrayDataSource("hello".getBytes(), "text/plain");
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setDataHandler(new DataHandler(source));
				attachment.setFileName("invite.ical");
				multiPart.addBodyPart(attachment);
			}

			message.setContent(multiPart);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

		} catch (AddressException addressException) {
			System.out.println(addressException);
		} catch (MessagingException messagingExcetion) {
			System.out.println(messagingExcetion);
		}
	}

}
