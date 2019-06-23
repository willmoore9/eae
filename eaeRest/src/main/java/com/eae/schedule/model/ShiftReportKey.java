package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ShiftReportKey implements Serializable {
	
	static final long serialVersionUID = 1L;

	String shiftGuid;

	String scheduleGuid;
	
	public String getScheduleGuid() {
		return scheduleGuid;
	}

	public String getShiftGuid() {
		return shiftGuid;
	}

	public ShiftReportKey() {
	}
	
	public ShiftReportKey(String shift, String schedule) {
		shiftGuid = shift;
		scheduleGuid = schedule;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (shiftGuid + scheduleGuid).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!( obj instanceof ShiftReportKey)) 
		return false;
		
		ShiftReportKey key = (ShiftReportKey) obj;
		
		return shiftGuid.equalsIgnoreCase(key.shiftGuid) && scheduleGuid.equalsIgnoreCase(key.scheduleGuid);
	}
}
