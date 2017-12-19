package com.eae.schedule.ui.model;

import java.util.List;

import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.ServicePeriod;

public class LandingDTO {
	private Publisher publisher;
	private ServicePeriod currentPeriod;
	private List<CartSchedule> sharedSchedules;
	
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	public ServicePeriod getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(ServicePeriod currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	public List<CartSchedule> getSharedSchedules() {
		return sharedSchedules;
	}
	public void setSharedSchedules(List<CartSchedule> sharedSchedules) {
		this.sharedSchedules = sharedSchedules;
	}

}
