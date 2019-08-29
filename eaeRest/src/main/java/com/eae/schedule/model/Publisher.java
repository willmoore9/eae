package com.eae.schedule.model;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="PUBLISHER")
public class Publisher extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1606288098368799204L;

	public Publisher() {
	}
	
	public Publisher(String guid) {
		this.setGuid(guid);
	}
	
	@Column(name="NAME",length = 56)
	private String name;
	
	@Column(name="SURNAME", length = 56)
	private String surname;
	
	@Column(name="CONGREGATION", length = 158)
	private String congregation;
	
	@Column(name="EMAIL", length = 56)
	private String email;
	
	@Column(name="PASSWORD", length = 56)
	@JsonIgnore
	private String password;
	
	@Column(name="TELEPHONE", length = 56)
	private String telephone;

	@Column(name="IS_ADMIN")
	private Boolean isAdmin = false;

	@Column(name="PIN_CODE", length = 8)
	private Integer pinCode = 101914;
	
	@Column(name="LANG", length = 8)
	private String language;
	
	@OneToOne(cascade={CascadeType.REMOVE}, fetch=FetchType.EAGER, optional=true)
	@JoinColumn(name="CONSENT_GUID")
	private Consent consent;
	
	@Transient
	private Boolean wasAssignedToday;
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name = "PUBLISHER_CART_ASSIGN",
			joinColumns = { @JoinColumn(name="PUBLISHER_GUID",referencedColumnName="GUID") },
			inverseJoinColumns = {@JoinColumn(name="CART_GUID", referencedColumnName="GUID")}
	)
	private List<CartPoint> carts;
	
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
		this.email = email.toLowerCase(Locale.ENGLISH);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public Boolean getWasAssignedToday() {
		return wasAssignedToday;
	}

	public void setWasAssignedToday(Boolean wasAssignedToday) {
		this.wasAssignedToday = wasAssignedToday;
	}

	public Consent getConsent() {
		return consent;
	}

	public void setConsent(Consent concent) {
		this.consent = concent;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<CartPoint> getCarts() {
		return carts;
	}

	public void setCarts(List<CartPoint> carts) {
		this.carts = carts;
	}
	
	
	
}
