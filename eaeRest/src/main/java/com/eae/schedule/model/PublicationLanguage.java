package com.eae.schedule.model;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: PublicationLanguage
 *
 */
@Entity
@Table(name="LANGUAGES")
public class PublicationLanguage extends BaseObject implements Serializable {

	@Basic
	@Column(name="ISO_CODE",length = 4)
	private String isoCode;
	
	@Basic
	@Column(name="WT_CODE",length = 8)
	private String wtCode;
	
	@Basic
	@Column(name="LANG_NAME",length = 128)
	private String langName;
	
	@Basic
	@Column(name="ORIGINAL_LANG_NAME",length = 128)
	private String originaLangName;
	
	
	private static final long serialVersionUID = 1L;

	public PublicationLanguage() {
		super();
	}

	public PublicationLanguage(String guid) {
		this.setGuid(guid);
	}
	
	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public String getWtCode() {
		return wtCode;
	}

	public void setWtCode(String wtCode) {
		this.wtCode = wtCode;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	public String getOriginaLangName() {
		return originaLangName;
	}

	public void setOriginaLangName(String originaLangName) {
		this.originaLangName = originaLangName;
	}
	
	
   
}
