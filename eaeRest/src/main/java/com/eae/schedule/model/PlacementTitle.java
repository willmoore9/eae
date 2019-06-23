package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name="PLACEMENT_TITLE")
public class PlacementTitle extends BaseObject implements Serializable {
	
	@ManyToOne(fetch=FetchType.LAZY, cascade= {CascadeType.DETACH})
	@JoinColumn(name="PLACEMENT_GUID", nullable=false)
	@JsonBackReference
	private Placement placement;

	@OneToOne(optional=true, cascade={CascadeType.DETACH})
	private PublicationLanguage language;
	
	@Basic
	@Column(length=128, name="TITLE")
	private String title;

	private static final long serialVersionUID = 1L;
	
	public PlacementTitle() {
		super();
	}

	public PlacementTitle(PublicationLanguage lang, String publicationTitle) {
		this.language = lang;
		this.title = publicationTitle;
	}
	
	public PublicationLanguage getLanguage() {
		return language;
	}

	public void setLanguage(PublicationLanguage language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	
	
	
}
