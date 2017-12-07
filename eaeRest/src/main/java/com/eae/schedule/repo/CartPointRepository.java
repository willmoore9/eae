package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eae.schedule.model.CartPoint;

public interface CartPointRepository extends PagingAndSortingRepository<CartPoint, String> {
	List<CartPoint> findCartPointByName(String name);
}
