package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.ServicePeriod;


public interface ServiceDayRepository extends JpaRepository<ServiceDay, String> {
	@EntityGraph(type = EntityGraphType.LOAD, attributePaths={"shifts"})
//	@EntityGraph("shifts")
	List<ServiceDay> findServiceDayByPeriod(ServicePeriod periodId, Sort sort);
//	  @Query(name="ServiceDay.findDaysInPeriod")
//	  List<ServiceDay> findServiceDayByPeriodWithShifts(String periodId);
}
