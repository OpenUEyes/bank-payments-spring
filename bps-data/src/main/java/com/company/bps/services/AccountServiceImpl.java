package com.company.bps.services;

import com.company.bps.model.Account;
import com.company.bps.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Set<Account> findAll() {
        Set<Account> accounts = new HashSet<>();
        repository.findAll().forEach(accounts::add);
        return accounts;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Account account) {
        repository.delete(account);
    }

    @Override
    public Optional<Account> findByLoginAndPassword(String login, String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    @Override
    public boolean existsByLoginOrEmailOrPhoneNumber(String login, String email, String phoneNumber) {
        return repository.existsByLoginOrEmailOrPhoneNumber(login, email, phoneNumber);
    }
}