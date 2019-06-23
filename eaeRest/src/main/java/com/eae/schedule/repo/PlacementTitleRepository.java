package com.eae.schedule.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.PlacementTitle;


public interface PlacementTitleRepository extends JpaRepository<PlacementTitle, String> {
}
