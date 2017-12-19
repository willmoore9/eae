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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;


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
	@Column(name="SHIFT_TEMPLATE")
	private String shiftTemplate;
	
	@OneToMany(mappedBy="period", cascade={CascadeType.DETACH}, fetch=FetchType.EAGER)
	@JsonIgnore
	private List<ServiceDay> serviceDays = new ArrayList<ServiceDay>();

	@Transient
	@JsonInclude
	private Integer timeOffset; 
	
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

	public String getShiftTemplate() {
		return shiftTemplate;
	}

	public void setShiftTemplate(String shiftTemplate) {
		this.shiftTemplate = shiftTemplate;
	}

	public Boolean getIsShared() {
		return isShared;
	}

	public void setIsShared(Boolean isShared) {
		this.isShared = isShared;
	}

	public Integer getTimeOffset() {
		return timeOffset;
	}

	public void setTimeOffset(Integer timeOffset) {
		this.timeOffset = timeOffset;
	}
}
