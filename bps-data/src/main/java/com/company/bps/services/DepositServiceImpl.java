package com.company.bps.services;

import com.company.bps.model.Deposit;
import com.company.bps.repositories.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class DepositServiceImpl implements DepositService {

    private final DepositRepository repository;

    public DepositServiceImpl(DepositRepository repository) {
        this.repository = repository;
    }

    @Override
    public Deposit save(Deposit deposit) {
        return repository.save(deposit);
    }

    @Override
    public Deposit findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Set<Deposit> findAll() {
        Set<Deposit> deposits = new HashSet<>();
        repository.findAll().forEach(deposits::add);
        return deposits;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Deposit deposit) {
        repository.delete(deposit);
    }
}