package com.eae.schedule.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.Placement;


public interface PlacementsRepository extends JpaRepository<Placement, String> {
}
