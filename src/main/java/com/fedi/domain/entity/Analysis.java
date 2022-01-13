package com.fedi.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "analysis")
public class Analysis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "analysis_id")
	private Long analysisId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private Image image;
	
	private Double similarity;
	
	@Column(name = "input_vector")
	private Double inputVector;
	
	@Builder
	public Analysis(Image image, Double similarity, Double inputVector) {
		this.image = image;
		this.similarity = similarity;
		this.inputVector = inputVector;
	}
}
