package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PUBLISHER")
public class Publisher extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="NAME",length = 56)
	private String name;
	
	@Column(name="SURNAME", length = 56)
	private String surname;
	
	@Column(name="CONGREGATION", length = 158)
	private String congregation;
	
	@Column(name="EMAIL", length = 56)
	private String email;
	
	@Column(name="TELEPHONE", length = 56)
	private String telephone;

	@Column(name="IS_ADMIN")
	private Boolean isAdmin = false;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCongregation() {
		return congregation;
	}

	public void setCongregation(String congregation) {
		this.congregation = congregation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
