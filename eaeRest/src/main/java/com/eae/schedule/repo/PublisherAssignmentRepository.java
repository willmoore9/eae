package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.Publisher;
import com.eae.schedule.model.PublisherAssignment;

public interface PublisherAssignmentRepository extends JpaRepository<PublisherAssignment, String> {
	List<PublisherAssignment> findPublisherAssignmentByScheduleGuid(String guid);
	List<PublisherAssignment> findPublisherAssignmentByScheduleGuidAndShiftGuid(String schduleGuid, String shiftGuid);
	List<PublisherAssignment> findByPublisher(Publisher publisher);
	List<PublisherAssignment> findByPublisherAndScheduleGuidNotNullAndScheduleIsShared(Publisher publisher, boolean isScheduleShared);
}
