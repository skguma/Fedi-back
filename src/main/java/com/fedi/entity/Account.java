package com.fedi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
	
	@Id
	@Column(name = "account_id")
	private String accountId;
	
	@Column(name = "account_name")
	private String accountName;
	
	@Builder
	public Account(String accountId, String accountName) {
		this.accountId = accountId;
		this.accountName = accountName;
	}
}
