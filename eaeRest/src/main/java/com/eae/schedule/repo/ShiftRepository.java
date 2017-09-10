package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.Shift;

public interface ShiftRepository extends CrudRepository<Shift, String> {
	List<Shift> findShiftByServiceDay(ServiceDay day, Sort sort);
}
