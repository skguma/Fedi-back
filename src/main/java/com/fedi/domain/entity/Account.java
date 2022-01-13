package com.fedi.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name="account")
public class Account {
    @Id
    private String accountId;

    @Column
    private String accountName;

    @Builder
    public Account(String accountId, String accountName){
        this.accountId = accountId;
        this.accountName = accountName;
    }
}
