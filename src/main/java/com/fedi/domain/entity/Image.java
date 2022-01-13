package com.fedi.domain.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tweet_id")
	private Tweet tweet;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	private Double vector;
	
	private String eyes;
	
	@OneToMany(mappedBy = "image")
	private List<Analysis> analyses = new ArrayList<>();
	
	@Builder
	public Image(Tweet tweet, String imageUrl, Double vector, String eyes) {
		this.tweet = tweet;
		this.imageUrl = imageUrl;
		this.vector = vector;
		this.eyes = eyes;
	}
}
