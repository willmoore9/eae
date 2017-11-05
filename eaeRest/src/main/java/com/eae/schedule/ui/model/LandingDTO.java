package com.eae.schedule.ui.model;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.ServicePeriod;

public class LandingDTO {
	private Publisher publisher;
	private ServicePeriod currentPeriod;
	
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
	
	
	
}
