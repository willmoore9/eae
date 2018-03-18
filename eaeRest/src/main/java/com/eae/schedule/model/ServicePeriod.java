package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="SERVICE_PERIOD")
public class ServicePeriod extends BaseObject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name="NAME")
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name="PERIOD_STARTS")
	private Date starts;
	
	@Temporal(TemporalType.DATE)
	@Column(name="PERIOD_ENDS")
	private Date ends;
	
	@Column(name="IS_SHARED")
	private Boolean isShared = false;
	
	@Basic
	@Column(name="FIRST_SHIFT_START")
	private String firstShiftStart;
	
	@Basic
	@Column(name="NUMBER_OF_SHIFTS")
	private Integer numberOfShifts;
	
	@OneToMany(mappedBy="period", cascade={CascadeType.DETACH, CascadeType.REMOVE}, fetch=FetchType.EAGER, orphanRemoval=true)
//	@JsonIgnore
	private List<ServiceDay> serviceDays = new ArrayList<ServiceDay>();

	@Transient
	private Integer zoneOffset; 
	
	public ServicePeriod() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceDay> getServiceDays() {
		return serviceDays;
	}

	public void setServiceDays(List<ServiceDay> serviceDays) {
		this.serviceDays = serviceDays;
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

	public Boolean getIsShared() {
		return isShared;
	}

	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	public Integer getZoneOffset() {
		return zoneOffset;
	}

	public void setZoneOffset(Integer zoneOffset) {
		this.zoneOffset = zoneOffset;
	}

	public Integer getNumberOfShifts() {
		return numberOfShifts;
	}

	public void setNumberOfShifts(Integer numberOfShifts) {
		this.numberOfShifts = numberOfShifts;
	}

	public String getFirstShiftStart() {
		return firstShiftStart;
	}

	public void setFirstShiftStart(String firstShiftStart) {
		this.firstShiftStart = firstShiftStart;
	}
}
