package com.youable.bank_server.account.domain.repository;

import com.youable.bank_server.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserId(Long id);
}
