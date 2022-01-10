package com.fedi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fedi.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
