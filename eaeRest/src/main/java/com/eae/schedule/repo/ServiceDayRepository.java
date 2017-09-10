package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.ServicePeriod;


public interface ServiceDayRepository extends CrudRepository<ServiceDay, String> {
//	@EntityGraph(value = "ServiceDay.shifts", type = EntityGraphType.FETCH)
	List<ServiceDay> findServiceDayByPeriod(ServicePeriod periodId, Sort sort);
//	  @Query("select d from ServiceDay d JOIN d.shifts s where d.period.guid = ?1 and s.serviceDay.guid = d.guid")
//	  List<ServiceDay> findServiceDayByPeriodWithShifts(String periodId);
}
