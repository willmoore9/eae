package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: PlacementType
 *
 */
@Entity(name="PLACEMENT_TYPE")
public class Placement extends BaseObject implements Serializable {

	@Basic
	@Column(length=32, name="TYPE")
	private String type;

	@Basic
	@Column(length=32, name="WT_INDEX")
	private String wtIndex;

	@Basic
	@Column(length=128, name="ENG_NAME")
	private String englishName;
	
//	@OneToOne(fetch=FetchType.EAGER)
//	@JoinTable(
//			name = "PLACEMENT_LANGUAGE",
//			joinColumns = { @JoinColumn(name="PLACEMENT_GUID",referencedColumnName="GUID") },
//			inverseJoinColumns = {@JoinColumn(name="LANGUAGE_GUID", referencedColumnName="GUID")}
//	)
	@OneToOne(optional=true, cascade={CascadeType.DETACH})
	private PublicationLanguage language;
	
	private static final long serialVersionUID = 1L;

	public Placement() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWtIndex() {
		return wtIndex;
	}

	public void setWtIndex(String wtIndex) {
		this.wtIndex = wtIndex;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public PublicationLanguage getLanguage() {
		return language;
	}

	public void setLanguage(PublicationLanguage language) {
		this.language = language;
	}
   
	
	
}
