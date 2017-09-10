package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.OneToOne;

public class ServiceTracker extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToOne
	private ServicePeriod period;
	
	private Publisher publisher;
	
	private Integer times;

	public ServicePeriod getPeriod() {
		return period;
	}

	public void setPeriod(ServicePeriod period) {
		this.period = period;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	
	
}
