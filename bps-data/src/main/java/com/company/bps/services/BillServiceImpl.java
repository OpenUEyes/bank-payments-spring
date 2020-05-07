package com.company.bps.services;

import com.company.bps.model.Bill;
import com.company.bps.repositories.BillRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository repository;

    public BillServiceImpl(BillRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bill save(Bill bill) {
        return repository.save(bill);
    }

    @Override
    public Bill findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Set<Bill> findAll() {
        Set<Bill> bills = new HashSet<>();
        repository.findAll().forEach(bills::add);
        return bills;
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Bill bill) {
        repository.delete(bill);
    }
}