package com.eae.schedule.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.CartSchedule;
import com.eae.schedule.model.Shift;
import com.eae.schedule.model.ShiftReport;
import com.eae.schedule.model.ShiftReportKey;


public interface ShiftReportRepository extends JpaRepository<ShiftReport, ShiftReportKey>{
	List<ShiftReport> findByShiftAndSchedule(Shift shift, CartSchedule schedule);
}
