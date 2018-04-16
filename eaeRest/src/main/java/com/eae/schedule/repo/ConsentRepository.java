package com.eae.schedule.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.Consent;

public interface ConsentRepository extends JpaRepository<Consent, String> {
}
