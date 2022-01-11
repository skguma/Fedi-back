package com.fedi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fedi.domain.entity.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

}
