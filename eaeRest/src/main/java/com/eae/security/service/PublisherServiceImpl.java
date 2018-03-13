package com.eae.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.repo.PublisherRepository;

@Service
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	private PublisherRepository publisherRepo;

	@Override
	public List<Publisher> findPublisherByEmail(String email) {
		return this.publisherRepo.findPublishersByEmail(email);
	}
	
	@Override
	public void saveTechUser(Publisher user) {
		this.publisherRepo.save(user);
	}

}
