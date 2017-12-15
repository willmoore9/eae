package com.eae.schedule.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.ServiceDay;
import com.eae.schedule.model.Shift;

public interface ShiftRepository extends JpaRepository<Shift, String> {
	List<Shift> findShiftByServiceDay(ServiceDay day, Sort sort);
	List<Shift> findShiftBy(ServiceDay day, Sort sort);
}
