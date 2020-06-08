package com.company.bps.services;

import com.company.bps.model.Account;
import com.company.bps.model.Bill;

import java.util.Optional;
import java.util.Set;

public interface AccountService extends CrudService<Account, Long> {

    Optional<Account> findByLoginAndPassword(String login, String password);

    boolean existsByLoginOrEmailOrPhoneNumber(String login, String email, String phoneNumber);
}