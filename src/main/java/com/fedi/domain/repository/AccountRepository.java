package com.fedi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fedi.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, String> {

}
