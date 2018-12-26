package com.eae.schedule.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eae.schedule.model.PublicationLanguage;


public interface PublicationLangsRepository extends JpaRepository<PublicationLanguage, String> {
	List<PublicationLanguage> findPublicationLanguageByIsoCode(String langCode);
	List<PublicationLanguage> findPublicationLanguageByWtCode(String wtCode);
	List<PublicationLanguage> findPublicationLanguageByLangName(String langName);
}
