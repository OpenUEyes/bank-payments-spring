package com.company.bps.services;

import com.company.bps.model.Credit;
import com.company.bps.repositories.CreditRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CreditServiceImpl implements CreditService {

    private final CreditRepository creditRepository;

    public CreditServiceImpl(CreditRepository creditRepository) {
        this.creditRepository = creditRepository;
    }

    @Override
    public Credit save(Credit credit) {
        return creditRepository.save(credit);
    }

    @Override
    public Credit findById(Long id) {
        return creditRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Credit> findAll() {
        Set<Credit> credits = new HashSet<>();
        creditRepository.findAll().forEach(credits::add);
        return credits;
    }

    @Override
    public void deleteById(Long id) {
        creditRepository.deleteById(id);
    }

    @Override
    public void delete(Credit credit) {
        creditRepository.delete(credit);
    }

    @Override
    public Optional<Credit> findByIdAndAccountId(Long billId, Long accountId) {
        Optional<Credit> optionalCredit = creditRepository.findById(billId);
        if (optionalCredit.isPresent()){
            Credit credit = optionalCredit.get();
            if (credit.getBill().getAccount().getId().equals(accountId)) {
                return optionalCredit;
            }
        }

        return Optional.empty();
    }
}