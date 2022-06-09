package com.fedi.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fedi.domain.entity.Analysis;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
	
	@Query(value = "SELECT a FROM Analysis a JOIN a.image i JOIN i.tweet t"
			+ " WHERE a.similarity >= :threshold and t.suspendFlag=false and a.analysisId in :analysisIds"
			+ " ORDER BY a.similarity DESC")
	public List<Analysis> findGreaterThan(@Param("threshold") Double threshold, @Param("analysisIds") List<Long> analysisIds
			, Pageable limit);
}
