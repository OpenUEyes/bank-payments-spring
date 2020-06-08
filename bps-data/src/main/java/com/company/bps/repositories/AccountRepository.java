package com.company.bps.repositories;

import com.company.bps.model.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findByLoginAndPassword(String login, String password);

    boolean existsByLoginOrEmailOrPhoneNumber(String login, String email, String phoneNumber);
}