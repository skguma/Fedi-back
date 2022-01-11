package com.fedi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;
	
	@Column(name = "tweet_id")
	private Long tweetId;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	private Double vector;
	
	private String eyes;
	
	@Builder
	public Image(Long tweetId, String imageUrl, Double vector, String eyes) {
		this.tweetId = tweetId;
		this.imageUrl = imageUrl;
		this.vector = vector;
		this.eyes = eyes;
	}
}
