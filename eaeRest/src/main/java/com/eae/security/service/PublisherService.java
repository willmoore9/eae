package com.eae.security.service;

import java.util.List;

import com.eae.schedule.model.Publisher;

public interface PublisherService {
	public List<Publisher> findPublisherByEmail(String email);
}
