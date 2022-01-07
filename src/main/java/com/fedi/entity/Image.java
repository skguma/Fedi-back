package com.fedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "image")
public class Image {
	@Id
	@Column(name = "image_id")
	private Long imageId;
	
	@Column(name = "tweet_id")
	private Long tweetId;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	private Double vector;
	
	private String eyes;
}
