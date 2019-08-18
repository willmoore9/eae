package com.eae.schedule.ui.model;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {

	private List<T> objects;
	
	private T object;
	
	private Boolean successful;
	
	private String status;
	
	private Long total;

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

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
	
	
	
}
