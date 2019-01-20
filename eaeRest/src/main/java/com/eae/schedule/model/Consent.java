package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="CONSENT")
public class Consent extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 1606288098368799204L;

	public Consent() {
	}
	
	@OneToOne(cascade = CascadeType.DETACH, mappedBy="consent")
	@JoinColumn(columnDefinition="PUBLISHER_GUID")
	@JsonBackReference
	private Publisher publisher;
	
	public Consent(String guid) {
		this.setGuid(guid);
	}
	
	@Enumerated(EnumType.STRING)
	private ConsentStatus status;

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public ConsentStatus getStatus() {
		return status;
	}

	public void setStatus(ConsentStatus status) {
		this.status = status;
	}
	
	
}
