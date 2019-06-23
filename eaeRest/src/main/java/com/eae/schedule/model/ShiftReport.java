package com.eae.schedule.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * Entity implementation class for Entity: ShiftReport
 *
 */
@Entity(name="SHIFT_REPORT")
public class ShiftReport implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ShiftReportKey key; 
//	
//	@Id
//	private String guid;
	@OneToOne(cascade={CascadeType.DETACH}, fetch=FetchType.EAGER)
	@JoinColumn(name="SHIFTGUID", referencedColumnName="GUID", insertable=false, updatable=false)			
//	@MapsId(value="shiftGuid")
	private Shift shift;
		
	@OneToOne(cascade={CascadeType.DETACH}, fetch=FetchType.EAGER)
	@JoinColumn(name="SCHEDULEGUID", referencedColumnName="GUID", insertable=false, updatable=false)			
//	@MapsId(value="scheduleGuid")
	private CartSchedule schedule;
	
	@OneToMany(mappedBy="report", cascade={CascadeType.REMOVE}, fetch=FetchType.EAGER, orphanRemoval=true)
	@JsonIgnore
	private List<ShiftReportItem> items = new ArrayList<>();

	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE")
	private Date created;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE")
	private Date updated;
	
	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	public CartSchedule getSchedule() {
		return schedule;
	}

	public void setSchedule(CartSchedule schedule) {
		this.schedule = schedule;
	}

	public List<ShiftReportItem> getItems() {
		return items;
	}

    @PrePersist
    protected void generateAuditInformation() 
    {   
        final Date now = new Date();
        
        this.created = now;
        this.updated = now;
        
        // TODO - obtain currently logged-on user
    }
	public void setItems(List<ShiftReportItem> items) {
		this.items = items;
	}

	public ShiftReportKey getKey() {
		return key;
	}

	public void setKey(ShiftReportKey key) {
		this.key = key;
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
	
	public ShiftReportItem findReportItem(String placementGuid) {
		List<ShiftReportItem> items = this.getItems();
		Optional<ShiftReportItem> targetItemOptional = items.stream().filter(item -> item.getPlacement().getGuid().equals(placementGuid)).findFirst();
		return targetItemOptional.orElse(null);
	}
}
