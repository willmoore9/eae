package com.eae.schedule.repo;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eae.schedule.model.CartSchedule;

public interface CartScheduleRepository extends PagingAndSortingRepository<CartSchedule, String> {
}
