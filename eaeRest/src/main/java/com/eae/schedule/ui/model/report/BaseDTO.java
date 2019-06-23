package com.eae.schedule.ui.model.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.eae.schedule.model.ShiftReportKey;

public class BaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String guid;
	
	private int count;
	
	private String displayCode;
	
	private List<BaseDTO> children = new ArrayList<BaseDTO>();;
	
	private String type;
	
	private ShiftReportKey key;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDisplayCode() {
		return displayCode;
	}

	public void setDisplayCode(String displayCode) {
		this.displayCode = displayCode;
	}

	public List<BaseDTO> getChildren() {
		return children;
	}

	public void setChildren(List<BaseDTO> children) {
		this.children = children;
	}

	public Boolean addChild(BaseDTO child) {
		return this.children.add(child);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public ShiftReportKey getKey() {
		return key;
	}

	public void setKey(ShiftReportKey key) {
		this.key = key;
	}
	
	
}
