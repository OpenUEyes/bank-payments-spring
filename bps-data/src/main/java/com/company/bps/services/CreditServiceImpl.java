package com.company.bps.services;

import com.company.bps.model.Credit;
import com.company.bps.repositories.CreditRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository repository;

    public CreditServiceImpl(CreditRepository repository) {
        this.repository = repository;
    }

    @Override
    public Credit save(Credit credit) {
        return repository.save(credit);
    }

    @Override
    public Credit findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Set<Credit> findAll() {
        Set<Credit> credits = new HashSet<>();
        repository.findAll().forEach(credits::add);
        return credits;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Credit credit) {
        repository.delete(credit);
    }
}