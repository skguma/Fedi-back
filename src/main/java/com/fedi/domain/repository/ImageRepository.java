package com.fedi.domain.repository;


import com.fedi.domain.entity.Image;
import com.fedi.domain.entity.Tweet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {
	
	@Query(value = "SELECT t FROM Image i JOIN i.tweet t WHERE i.imageId in :imageIds")
	List<Tweet> findTweetUrlByImageId(@Param("imageIds") List<Long> ids);
  
}
