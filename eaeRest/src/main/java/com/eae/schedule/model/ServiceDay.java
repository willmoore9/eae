package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

@Entity
@Table(name="SERVICE_DAY")
@NamedEntityGraph(name = "ServiceDay.shifts",
attributeNodes = @NamedAttributeNode("shifts"))
public class ServiceDay extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DATE")
	private Date date;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="SERVICE_PERIOD_ID")
	private ServicePeriod period;

//	@OneToMany
	@OneToMany(cascade={CascadeType.ALL}, mappedBy="serviceDay", fetch=FetchType.EAGER)
	@JoinFetch(JoinFetchType.OUTER)
	private Collection<Shift> shifts;

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

	public Collection<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	public Shift addShift(Shift shift) {
		getShifts().add(shift);
		shift.setServiceDay(this);
		return shift;
	}
	
}
