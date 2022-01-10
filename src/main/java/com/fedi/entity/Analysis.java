package com.fedi.entity;

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
@Table(name = "analysis")
public class Analysis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "analysis_id")
	private Long analysisId;
	
	@Column(name = "image_id")
	private Long imageId;
	
	private Double similarity;
	
	@Column(name = "input_vector")
	private Double inputVector;
	
	@Builder
	public Analysis(Long imageId, Double similarity, Double inputVector) {
		this.imageId = imageId;
		this.similarity = similarity;
		this.inputVector = inputVector;
	}
}
