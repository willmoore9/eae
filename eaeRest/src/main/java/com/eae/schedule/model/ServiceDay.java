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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name="SERVICE_DAY")
public class ServiceDay extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE")
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="GUID")
	private ServicePeriod period;

	@OneToMany(mappedBy="serviceDay", fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinFetch(JoinFetchType.OUTER)
	@JsonManagedReference
	private List<Shift> shifts = new ArrayList<Shift>();

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
}
