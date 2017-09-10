package com.eae.schedule.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
//import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
@PrimaryKeyJoinColumn(name="GUID")
public class BaseObject {

	@Id
    @Column(name="GUID", length = 36)
    private String guid = UUID.randomUUID().toString();
    
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE")
	private Date created;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE")
	private Date updated;

	public String getGuid() {
		return this.guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
    @PrePersist
    protected void generateAuditInformation() 
    {   
        final Date now = new Date();
        
        this.created = now;
        this.updated = now;
        
        // TODO - obtain currently logged-on user
    }
}
