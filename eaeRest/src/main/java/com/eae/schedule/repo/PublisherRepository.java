package com.eae.schedule.repo;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.eae.schedule.model.Publisher;

public interface PublisherRepository extends PagingAndSortingRepository<Publisher, String> {
	Publisher findPublisherByEmail(String email);
}
