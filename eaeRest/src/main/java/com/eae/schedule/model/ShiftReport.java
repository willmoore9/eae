package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entity implementation class for Entity: ShiftReport
 *
 */
@Entity(name="SHIFT_REPORT")
public class ShiftReport extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="STARTS")
	private Date starts;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ENDS")
	private Date ends;
	
	@ManyToOne(optional=true, cascade = {CascadeType.REMOVE})
    @JoinTable(name = "REPORT_SHIFT",
    	joinColumns = { @JoinColumn(name = "REPORT_ID", referencedColumnName = "GUID", nullable = true) },
    	inverseJoinColumns = { @JoinColumn(name = "SHIFT_ID", referencedColumnName = "GUID", nullable = true) }
    )		
	private Shift shift;
		
	@ManyToOne(optional=true, cascade = {CascadeType.REMOVE})
    @JoinTable(name = "REPORT_SCHEDULE",
    	joinColumns = { @JoinColumn(name = "REPORT_ID", referencedColumnName = "GUID", nullable = true) },
    	inverseJoinColumns = { @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "GUID", nullable = true) }
    )	
	private CartSchedule schedule;
	
	@OneToMany(mappedBy="report", cascade = { CascadeType.REMOVE }, fetch=FetchType.EAGER, orphanRemoval=true)
	@JsonIgnore
	private List<ShiftReportItem> items = new ArrayList<>();

	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.ends = shift.getEnds();
		this.starts = shift.getStarts();
		this.shift = shift;
	}

	public CartSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CartSchedule schedule) {
		this.schedule = schedule;
	}

	public List<ShiftReportItem> getItems() {
		return items;
	}

	public void setItems(List<ShiftReportItem> items) {
		this.items = items;
	}

	public ShiftReportItem findReportItem(String placementGuid) {
		List<ShiftReportItem> items = this.getItems();
		Optional<ShiftReportItem> targetItemOptional = items.stream().filter(item -> item.getPlacement().getGuid().equals(placementGuid)).findFirst();
		return targetItemOptional.orElse(null);
	}
}
