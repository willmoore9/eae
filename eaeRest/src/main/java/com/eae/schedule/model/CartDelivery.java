package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="CART_DELIVERY")
public class CartDelivery implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId 
	CartDeliveryKey key;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SERVICE_DAY_GUID", insertable = false, updatable = false)
	@JsonBackReference
	private ServiceDay serviceDay;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SCHEDULE_GUID", insertable = false, updatable = false)
	private CartSchedule schedule;
	
	@Column(name="DELIVER_TO",length = 255)
	private String location;

	public ServiceDay getServiceDay() {
		return serviceDay;
	}

	public void setServiceDay(ServiceDay serviceDay) {
		this.serviceDay = serviceDay;
	}

	public CartSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CartSchedule schedule) {
		this.schedule = schedule;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public CartDeliveryKey getKey() {
		return key;
	}

	public void setKey(CartDeliveryKey key) {
		this.key = key;
	}
	
}
