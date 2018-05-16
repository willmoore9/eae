package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.scheduling.annotation.Scheduled;

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
	
	@OneToMany(mappedBy="shift", fetch=FetchType.EAGER, cascade={CascadeType.REMOVE})
	@JoinFetch(JoinFetchType.OUTER)
	private List<PublisherAssignment> assignments = new ArrayList<PublisherAssignment>();

	public List<PublisherAssignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<PublisherAssignment> assignments) {
		this.assignments = assignments;
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

	public List<PublisherAssignment> filterBySchedule(CartSchedule schedule) {
		List<PublisherAssignment> assinments = this.getAssignments();
		List<PublisherAssignment> scheduleAssignments = new ArrayList<PublisherAssignment>();
		for(int i = 0; i < assinments.size(); i++) {
			PublisherAssignment assignment = assinments.get(i);
			if (assignment.getSchedule() != null && 
				assignment.getSchedule().getGuid().equals(schedule.getGuid())) {
				scheduleAssignments.add(assignment);
			}
		}
		
		return scheduleAssignments;
//		return this.getAssignments().stream().filter(assignment -> assignment.getSchedule() != null && assignment.getSchedule().getGuid().equals(schedule.getGuid())).collect(Collectors.toList());
	}
//	public List<Publisher> getAssignable() {
//		return assignable;
//	}
//
//	public void setAssignable(List<Publisher> assignable) {
//		this.assignable = assignable;
//	}
//
//	public Publisher getShiftLeader() {
//		return shiftLeader;
//	}
//
//	public void setShiftLeader(Publisher shiftLeader) {
//		this.shiftLeader = shiftLeader;
//	}
//
//	public Publisher getTrolleyCarrier() {
//		return trolleyCarrier;
//	}
//
//	public void setTrolleyCarrier(Publisher trolleyCarrier) {
//		this.trolleyCarrier = trolleyCarrier;
//	}
}
