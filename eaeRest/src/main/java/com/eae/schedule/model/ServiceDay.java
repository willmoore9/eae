package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.Oneway;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="SERVICE_DAY")
public class ServiceDay extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

//	@Column(name="DELIVER_TO",length = 16)
//	private String deliverTo;
//	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE")
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="GUID")
	@JsonBackReference
	private ServicePeriod period;

	@OneToMany(mappedBy="serviceDay", fetch=FetchType.EAGER, cascade={CascadeType.REMOVE})
	@OrderBy("starts ASC")
	@JsonManagedReference
	private List<Shift> shifts = new ArrayList<Shift>();

	
	@OneToMany(mappedBy="serviceDay", cascade={CascadeType.REMOVE})
	@JoinColumn(name="SERVICE_DAY_GUID")
	private List<CartDelivery> deliverTo;
	
	public ServiceDay () {
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ServicePeriod getPeriod() {
		return period;
	}

	public void setPeriod(ServicePeriod period) {
		this.period = period;
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	public List<CartDelivery> getDeliverTo() {
		return deliverTo;
	}

	public void setDeliverTo(List<CartDelivery> deliverTo) {
		this.deliverTo = deliverTo;
	}
	
	
}
