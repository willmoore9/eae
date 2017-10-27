package com.eae.security.rest;

import org.springframework.security.core.GrantedAuthority;

public class PublisherAuthority implements GrantedAuthority{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		return "PUBLISHER";
	};

}
