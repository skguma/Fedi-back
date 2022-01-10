package com.fedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fedi.entity.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

}
