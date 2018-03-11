package com.eae.schedule.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
	Publisher findPublisherByEmail(String email);
}
