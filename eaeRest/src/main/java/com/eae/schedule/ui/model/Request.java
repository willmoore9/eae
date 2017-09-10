package com.eae.schedule.ui.model;

public class Request<T> {

	private T object;
	
	
	public Request(T obj) {
		this.object = obj;
	}
	
	public Request() {
		
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	
}
