package com.eae.security.service;

import com.eae.schedule.model.Publisher;

public interface PublisherService {
	public Publisher findPublisherByEmail(String email);
}
