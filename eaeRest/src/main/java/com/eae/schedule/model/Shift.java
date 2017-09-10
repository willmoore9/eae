package com.eae.schedule.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

@Entity
@Table(name="SHIFT")
public class Shift extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="SERVICEDAY_GUID", nullable=false)
	private ServiceDay serviceDay;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STARTS")
	private Date starts;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDS")
	private Date ends;
	
//	@OneToOne(fetch=FetchType.EAGER, optional=true)
//	@JoinColumn(name="GUID", updatable=false, insertable=false, referencedColumnName="GUID")
//	private Publisher shiftLeader;


	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "SHIFT_PUBLISHER",
			joinColumns = { @JoinColumn(name="SHIFT_GUID",referencedColumnName="GUID") },
			inverseJoinColumns = {@JoinColumn(name="PUBLISHER_GUID", referencedColumnName="GUID")}
	)
	@JoinFetch(JoinFetchType.OUTER)
	private List<Publisher> assigned;
	
//	@OneToMany(mappedBy="shift")
//	private List<Availability> available;

//	public Publisher getShiftLeader() {
//		return shiftLeader;
//	}
//
//	public void setShiftLeader(Publisher shiftLeader) {
//		this.shiftLeader = shiftLeader;
//	}

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
	
	
	
}
