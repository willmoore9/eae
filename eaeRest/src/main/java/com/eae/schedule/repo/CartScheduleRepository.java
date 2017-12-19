package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eae.schedule.model.CartSchedule;

public interface CartScheduleRepository extends PagingAndSortingRepository<CartSchedule, String> {
	List<CartSchedule> findCartScheduleByIsShared(boolean isShared);
}
