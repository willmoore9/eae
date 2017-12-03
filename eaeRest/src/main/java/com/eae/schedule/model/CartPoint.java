package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CART_POINT")
@Cacheable(value=true)
public class CartPoint extends BaseObject implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name="NAME",length = 56)
	private String name;
	
	@Column(name="DESCRIPTION",length = 56)
	private String description;
	
	@Column(name="ADDRESS",length = 56)
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
}
