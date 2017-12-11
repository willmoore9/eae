package com.eae.schedule.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.PublisherAssignment;

public interface PublisherAssignmentRepository extends JpaRepository<PublisherAssignment, String> {
}
