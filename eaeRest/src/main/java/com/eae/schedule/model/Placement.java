package com.eae.schedule.model;

import com.eae.schedule.model.BaseObject;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PlacementType
 *
 */
@Entity(name="PLACEMENT_TYPE")
public class Placement extends BaseObject implements Serializable {

	@Basic
    @Enumerated(EnumType.STRING)
	@Column(length=32, name="TYPE")
	private PlementType type;
	

	@Basic
	@Column(length=32, name="WT_INDEX")
	private String wtIndex;

	@Basic
	@Column(length=128, name="ENG_NAME")
	private String englishName;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinTable(
			name = "PLACEMENT_LANGUAGE",
			joinColumns = { @JoinColumn(name="LANGUAGE_GUID",referencedColumnName="GUID") },
			inverseJoinColumns = {@JoinColumn(name="PLACEMENT_GUID", referencedColumnName="GUID")}
	)
	private PublicationLanguage language;
	
	private static final long serialVersionUID = 1L;

	public Placement() {
		super();
	}

	public PlementType getType() {
		return type;
	}

	public void setType(PlementType type) {
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
