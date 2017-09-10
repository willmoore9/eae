package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
//
//@Entity
//@Table(name="AVAILABILITY")
public class Availability extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Publisher pubisher;
	private Shift shift;
	private Boolean available;
	
	public Publisher getPubisher() {
		return pubisher;
	}
	public void setPubisher(Publisher pubisher) {
		this.pubisher = pubisher;
	}
	public Shift getShift() {
		return shift;
	}
	public void setShift(Shift shift) {
		this.shift = shift;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	
}
