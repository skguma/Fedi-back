package com.fedi.domain.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@Column
	private String accountId;
	
	@Column
	private String accountName;
	
<<<<<<< Updated upstream
=======
//	@OneToMany(mappedBy = "account")
//	private List<Tweet> tweets = new ArrayList<>();
	
>>>>>>> Stashed changes
	@Builder
	public Account(String accountId, String accountName) {
		this.accountId = accountId;
		this.accountName = accountName;
	}

}
