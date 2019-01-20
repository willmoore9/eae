package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity(name="REPORT_ITEM")
public class ShiftReportItem extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns(value= {
			@JoinColumn(name="SCHEDULE_GUID", referencedColumnName="GUID"),
			@JoinColumn(name="SHIFT_GUID", referencedColumnName="GUID")
	})
	@JsonBackReference
	private ShiftReport report;
	
	
	@OneToOne(optional=true, cascade={CascadeType.DETACH})
	private Placement placement;
	
	@Basic
	private Integer count;
	
	public ShiftReport getReport() {
		return report;
	}

	public void setReport(ShiftReport report) {
		this.report = report;
	}

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
}
