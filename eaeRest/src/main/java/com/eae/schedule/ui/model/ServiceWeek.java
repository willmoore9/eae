package com.eae.schedule.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eae.schedule.model.ServiceDay;

public class ServiceWeek implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private List<ServiceDay> weekDays;
	
	public ServiceWeek() {
		this.weekDays = new ArrayList<ServiceDay>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceDay> getWeekDays() {
		return weekDays;
	}

	public void setWeekDays(List<ServiceDay> weekDays) {
		this.weekDays = weekDays;
	}
}
