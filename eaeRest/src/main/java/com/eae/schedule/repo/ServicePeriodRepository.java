package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eae.schedule.model.ServicePeriod;

public interface ServicePeriodRepository extends PagingAndSortingRepository<ServicePeriod, String> {
	List<ServicePeriod> findServicePeriodByIsShared(boolean isShared);
}
