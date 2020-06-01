package com.company.bps.services;

import com.company.bps.model.Account;

import java.util.Optional;

public interface AccountService extends CrudService<Account, Long> {

    Optional<Account> findByLoginAndPassword(String login, String password);
    boolean existsByLoginOrEmailOrPhoneNumber(String login, String email, String phoneNumber);
}