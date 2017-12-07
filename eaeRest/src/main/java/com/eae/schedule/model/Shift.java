package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="SHIFT")
public class Shift extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SERVICEDAY_GUID", nullable=false)
	@JsonBackReference
	private ServiceDay serviceDay;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STARTS")
	private Date starts;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDS")
	private Date ends;
	
	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinTable(
			name = "SHIFT_PUBLISHER",
			joinColumns = { @JoinColumn(name="SHIFT_GUID",referencedColumnName="GUID") },
			inverseJoinColumns = {@JoinColumn(name="PUBLISHER_GUID", referencedColumnName="GUID")}
	)
	
	@JoinFetch(JoinFetchType.OUTER)
	private List<Publisher> assigned = new ArrayList<Publisher>();

	@OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinTable(
			name = "SHIFT_PUBLISHER_ASSIGN",
			joinColumns = { @JoinColumn(name="SHIFT_GUID",referencedColumnName="GUID") },
			inverseJoinColumns = {@JoinColumn(name="PUBLISHER_GUID", referencedColumnName="GUID")}
	)
	@JoinFetch(JoinFetchType.OUTER)
	private List<Publisher> assignable = new ArrayList<Publisher>();
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="GUID")
	private Publisher shiftLeader;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(referencedColumnName="GUID")
	private Publisher trolleyCarrier;
	
	public List<Publisher> getAssigned() {
		return assigned;
	}

	public void setAssigned(List<Publisher> assigned) {
		this.assigned = assigned;
	}

	public ServiceDay getServiceDay() {
		return serviceDay;
	}

	public void setServiceDay(ServiceDay serviceDay) {
		this.serviceDay = serviceDay;
	}

	public Date getStarts() {
		return starts;
	}

	public void setStarts(Date starts) {
		this.starts = starts;
	}

	public Date getEnds() {
		return ends;
	}

	public void setEnds(Date ends) {
		this.ends = ends;
	}

	public List<Publisher> getAssignable() {
		return assignable;
	}

	public void setAssignable(List<Publisher> assignable) {
		this.assignable = assignable;
	}

	public Publisher getShiftLeader() {
		return shiftLeader;
	}

	public void setShiftLeader(Publisher shiftLeader) {
		this.shiftLeader = shiftLeader;
	}

	public Publisher getTrolleyCarrier() {
		return trolleyCarrier;
	}

	public void setTrolleyCarrier(Publisher trolleyCarrier) {
		this.trolleyCarrier = trolleyCarrier;
	}
}
