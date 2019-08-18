package com.eae.schedule.repo;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.eae.schedule.model.ShiftReport;


public interface ShiftReportPageableRepository extends PagingAndSortingRepository<ShiftReport, String>{
}
