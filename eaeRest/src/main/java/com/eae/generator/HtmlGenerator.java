package com.eae.generator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.eae.schedule.model.PublisherAssignment;
import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.Shift;
import com.eae.schedule.ui.model.ServiceWeek;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class HtmlGenerator {

	private List<ServiceWeek> weeks;
	private String mainTmpl = "";
	private DateFormat dayFormat = new SimpleDateFormat("E, dd MMM yyyy");
	private DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	private Locale locale = Locale.ENGLISH;
	
	public HtmlGenerator(List<ServiceWeek> weeks) {
		this.weeks = weeks;
		byte [] mainTempl = null;
		try {
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream("emailTmpl/ScheduleMainTmpl.html");
			mainTempl = new byte[stream.available()];
			stream.read(mainTempl, 0, mainTempl.length);
			stream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainTmpl = new String(mainTempl);
		
	}
	
	public HtmlGenerator(List<ServiceWeek> weeks, Locale locale) {
		this(weeks);
		this.locale = locale;
		dayFormat = new SimpleDateFormat("E, dd MMM yyyy", this.locale ); 
	}
	
	public String generasteHtml() {
		StringBuffer weekStrBuffer = new StringBuffer();
		
		for(ServiceWeek week : this.weeks) {
			weekStrBuffer.append("<table width=\"1200px\">");
			weekStrBuffer.append("<caption>");
			weekStrBuffer.append("KW/CW");
			weekStrBuffer.append(week.getName());
			weekStrBuffer.append("</caption>");		
			weekStrBuffer.append("<br/>");
			
			boolean isTr = true;
			
			for(ServiceDay serviceDay : week.getWeekDays()) {
				StringBuffer dayStrBuffer = new StringBuffer();
				
				if(isTr) {
					dayStrBuffer.append("<tr>");	
				}
				Date day = serviceDay.getDate();
				
				dayStrBuffer.append("<td style=\"margin-bottom:10px\">");
				
				dayStrBuffer.append("<div style=\" width:300px; font-weight: bold;  font-size: 15px; padding-bottom: 5px;\"><u>" + dayFormat.format(new Date(day.getTime()))  + "</u></div>");

				for(Shift shift : serviceDay.getShifts()){
					dayStrBuffer.append("<div style=\" font-size: 13px; color: grey;  padding-bottom: 5px, padding-top: 5px\">");
					Date shiftStart = shift.getStarts();
					dayStrBuffer.append("<div style=\" font-size: 13px; font-weight: bold; color: cornflowerblue;  padding-bottom: 5px\">" + timeFormat.format(shiftStart) + "</div>");
					
					if(shift.getAssignments().isEmpty()) {
						dayStrBuffer.append("<div align=\"left\" style=\" padding-bottom: 5px;\"><u>" + "No Shift/Kein Shicht" + "</u></div>");
					}
					
					for(PublisherAssignment assignemnt : shift.getAssignments()) {
						String tel = assignemnt.getPublisher().getTelephone() == null ? "--" : assignemnt.getPublisher().getTelephone();
						String cong = assignemnt.getPublisher().getCongregation() == null ? "--" : assignemnt.getPublisher().getCongregation();
						String name = assignemnt.getPublisher().getName() == null ? "--" : assignemnt.getPublisher().getName();
						String surname = assignemnt.getPublisher().getSurname() == null ? "--" : assignemnt.getPublisher().getSurname();
						String tableCellInner = name + " " + surname + " (" + tel + ") " + cong;
						if(assignemnt.getIsShiftLeader()) {
							dayStrBuffer.append("<div align=\"left\" style=\"  padding-bottom: 5px;\"><u>" + tableCellInner + "</u></div>");	
						} else {
							dayStrBuffer.append("<div align=\"left\" style=\"  padding-bottom: 5px;\">" + tableCellInner + "</div>");
						}
						
					}
					dayStrBuffer.append("</div>");
					
				}
				
				dayStrBuffer.append("</td>");
				

				
				if(!isTr) {
					dayStrBuffer.append("</tr>");	
				}
				
				isTr = isTr ? false : true;
				
				weekStrBuffer.append(dayStrBuffer);
				
			}
			
			weekStrBuffer.append("</tr></table>");
			
		}
		
		return MessageFormat.format(mainTmpl, weekStrBuffer);	
	}
	
	public byte [] generatePdf () {
	   String sHtmlSchedule = this.generasteHtml();
	   Document document = new Document();
	   ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(sHtmlSchedule.getBytes()));
			document.close();
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		} 
		return outputStream.toByteArray();
	}
}
