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
@Table(name = "analysis")
public class Analysis {
	@Id
	@Column(name = "analysis_id")
	private Long analysisId;
	
	@Column(name = "image_id")
	private Long imageId;
	
	private Double similarity;
	
	@Column(name = "input_vector")
	private Double inputVector;
}
