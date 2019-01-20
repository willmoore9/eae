package com.eae.schedule.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.ShiftReportItem;


public interface ShiftReportItemRepository extends JpaRepository<ShiftReportItem, String> {
}
