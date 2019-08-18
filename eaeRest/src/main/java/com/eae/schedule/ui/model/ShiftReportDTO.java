package com.eae.schedule.ui.model;

import java.util.Date;

public class ShiftReportDTO {
	
	private String guid;
	private Date from;
	private Date to;
	private String trolley;
	private Integer placementsCount;
	private Integer videosCount;
	
	public ShiftReportDTO(String trolley, Date from, Date to, Integer placementsCount, Integer videosCount) {
		super();
		this.trolley = trolley;
		this.from = from;
		this.to = to;
		this.placementsCount = placementsCount;
		this.videosCount = videosCount;
	}
	
	public ShiftReportDTO() {

	}
	
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getTrolley() {
		return trolley;
	}
	public void setTrolley(String trolley) {
		this.trolley = trolley;
	}
	public Integer getPlacementsCount() {
		return placementsCount;
	}
	public void setPlacementsCount(Integer placementsCount) {
		this.placementsCount = placementsCount;
	}
	public Integer getVideosCount() {
		return videosCount;
	}
	public void setVideosCount(Integer videosCount) {
		this.videosCount = videosCount;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
	
}
