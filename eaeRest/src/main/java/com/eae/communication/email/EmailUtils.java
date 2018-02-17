package com.eae.communication.email;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

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

	public static void sendBulkInvite(String subject, String bodyContent, String summary, String location, Date start, Date end, List<String> emails) {
		Session mailSession = buildSession();
		
		try {
			Transport transport = mailSession.getTransport();

			ICalendar ical = new ICalendar();
			VEvent event = new VEvent();
			event.setDateStart(start);
			event.setDateEnd(end);
			event.setSummary(summary);
			event.setLocation(location);
			event.setOrganizer(SMTP_AUTH_USER);
			ical.addEvent(event);
			byte [] calendarPayload = null;
			ByteArrayOutputStream reader = new ByteArrayOutputStream();

				Biweekly.write(ical).go(reader);
				calendarPayload = reader.toByteArray();
				
			
			MimeMessage message = new MimeMessage(mailSession);
			message.setSubject(subject);

			
			for(String email : emails) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));	
			}

			transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

			MimeBodyPart body = new MimeBodyPart();
			body.setText(bodyContent);

			MimeMultipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(body);

			if (ical != null) {
				DataSource source = new ByteArrayDataSource(calendarPayload, "text/plain");
				MimeBodyPart attachment = new MimeBodyPart();
				attachment.setDataHandler(new DataHandler(source));
				attachment.setFileName("invite.ics");
				multiPart.addBodyPart(attachment);
			}

			message.setContent(multiPart);
			transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			transport.close();

		} catch (AddressException addressException) {
			System.out.println(addressException);
		} catch (MessagingException messagingExcetion) {
			System.out.println(messagingExcetion);
		} catch (IOException ioException) {
			System.out.println(ioException);
		}
	}
	
	
	
//	public static void sendInvite(String subject, String body, String summary, String organizer, String location, Date start, Date ends, List<String> emails) {
	
//	public static void sendInvite(Shift shift) {
//		ICalendar ical = new ICalendar();
//		VEvent event = new VEvent();
//		event.setDateStart(shift.getStarts());
//		event.setDateEnd(shift.getEnds());
//		event.setSummary("EAE service");
//		event.setLocation(shift.getAssignments().get(0).getSchedule().getCart().getAddress());
//		event.setOrganizer(SMTP_AUTH_USER);
//		ical.addEvent(event);
//		byte [] calendarPayload = null;
//		ByteArrayOutputStream reader = new ByteArrayOutputStream();
//		try {
//			Biweekly.write(ical).go(reader);
//			calendarPayload = reader.toByteArray();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		List<PublisherAssignment> assignments = shift.getAssignments();
//		List<String> emails = new ArrayList<String>();
//		for(PublisherAssignment assignment : assignments) {
//			if(!assignment.getIsInvitationSent()) {
//				emails.add(assignment.getPublisher().getEmail());
//				assignment.setIsInvitationSent(true);
//			}
//		}
//		
//		sendInvite("EAE Service","", calendarPayload, emails);
//	}

}
