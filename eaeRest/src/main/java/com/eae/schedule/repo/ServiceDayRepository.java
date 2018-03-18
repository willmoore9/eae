package com.eae.schedule.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.ServicePeriod;


public interface ServiceDayRepository extends JpaRepository<ServiceDay, String> {
	List<ServiceDay> findServiceDayByPeriod(ServicePeriod periodId, Sort sort);
	
	
	List<ServiceDay> findServiceDayByPeriodAndDateBetween(ServicePeriod periodId, Date after, Date before, Sort sort);
}
