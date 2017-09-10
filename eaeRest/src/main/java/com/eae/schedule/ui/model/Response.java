package com.eae.schedule.ui.model;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private List<T> objects;
	
	private Boolean successful;
	
	private String status;
	
	private Integer total;

	public Response() {
		this.objects = new ArrayList<T>();
	}
	
	public Boolean getSuccessful() {
		return successful;
	}

	public List<T> getObjects() {
		return objects;
	}

	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public boolean addObject(T t) {
		return this.objects.add(t);
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
