package com.eae.schedule.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
	List<Publisher> findPublishersByEmail(String email);
}
