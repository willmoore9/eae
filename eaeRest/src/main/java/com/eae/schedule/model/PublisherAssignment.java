package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="PUBLISHER_ASSIGNMENT")
public class PublisherAssignment extends BaseObject implements Serializable{

	private static final long serialVersionUID = 1L;

	public PublisherAssignment() {
	}
	
	public PublisherAssignment(String guid) {
		this.setGuid(guid);
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(referencedColumnName="GUID")
	@JsonBackReference
	private Shift shift;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinTable(
			name = "SHIFT_PUBLISHER_ASSIGN",
			joinColumns = { @JoinColumn(name="SHIFT_GUID",referencedColumnName="GUID") },
			inverseJoinColumns = {@JoinColumn(name="PUBLISHER_GUID", referencedColumnName="GUID")}
	)
	private Publisher publisher;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST})
	private CartSchedule schedule;

	@Column(name = "IS_SHIFT_LEADER", nullable = false)
	private Boolean isShiftLeader = false;
	
	@Column(name = "IS_CART_CARRIER", nullable = false)
	private Boolean isCartCarrier = false;;
	
	@Column(name = "IS_BOOKED", nullable = false)
	private Boolean isBooked = false;;
	
	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public CartSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CartSchedule schedule) {
		this.schedule = schedule;
	}

	public Boolean getIsShiftLeader() {
		return isShiftLeader;
	}

	public void setIsShiftLeader(Boolean isShiftLeader) {
		this.isShiftLeader = isShiftLeader;
	}

	public Boolean getIsCartCarrier() {
		return isCartCarrier;
	}

	public void setIsCartCarrier(Boolean isCartCarrier) {
		this.isCartCarrier = isCartCarrier;
	}

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}
}
