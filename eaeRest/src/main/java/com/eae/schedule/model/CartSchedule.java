package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="SCHEDULE")
public class CartSchedule extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CartSchedule() {
	}
	
	public CartSchedule(String guid) {
		this.setGuid(guid);
	}
	
	@OneToOne
	@JoinColumn(referencedColumnName="GUID")
	private CartPoint cart;
	
	@OneToOne
	@JoinColumn(referencedColumnName="GUID")
	private ServicePeriod period;

	public CartPoint getCart() {
		return cart;
	}

	public void setCart(CartPoint cart) {
		this.cart = cart;
	}

	public ServicePeriod getPeriod() {
		return period;
	}

	public void setPeriod(ServicePeriod period) {
		this.period = period;
	}
	
	
}
