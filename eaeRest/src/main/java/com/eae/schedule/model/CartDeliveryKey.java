package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CartDeliveryKey implements Serializable {
	
	static final long serialVersionUID = 1L;

	String serviceDayGuid;

	String scheduleGuid;
	public String getScheduleGuid() {
		return scheduleGuid;
	}

	public CartDeliveryKey() {
	}
	
	public CartDeliveryKey(String day, String schedule) {
		serviceDayGuid = day;
		scheduleGuid = schedule;
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (serviceDayGuid + scheduleGuid).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!( obj instanceof CartDeliveryKey)) 
		return false;
		
		CartDeliveryKey key = (CartDeliveryKey) obj;
		
		return key.serviceDayGuid.equalsIgnoreCase(key.serviceDayGuid) && scheduleGuid.equalsIgnoreCase(key.scheduleGuid);
	}

	public String getServiceDayGuid() {
		return serviceDayGuid;
	}
	
	
	
}
