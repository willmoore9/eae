package com.eae.security.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.repo.PublisherRepository;

public class PublisherServiceImpl implements PublisherService {

	@Autowired(required=true)
	private PublisherRepository publisherRepo;

	@Override
	public Publisher findPublisherByEmail(String email) {
	
		return publisherRepo.findPublisherByEmail(email);
	}
	
	
}
