package com.company.bps.services;

import com.company.bps.model.Deposit;
import com.company.bps.repositories.DepositRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class DepositServiceImpl implements DepositService {

    private final DepositRepository depositRepository;

    public DepositServiceImpl(DepositRepository depositRepository) {
        this.depositRepository = depositRepository;
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public Deposit findById(Long id) {
        return depositRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Deposit> findAll() {
        Set<Deposit> deposits = new HashSet<>();
        depositRepository.findAll().forEach(deposits::add);
        return deposits;
    }

    @Override
    public void deleteById(Long id) {
        depositRepository.deleteById(id);
    }

    @Override
    public void delete(Deposit deposit) {
        depositRepository.delete(deposit);
    }

    @Override
    public Optional<Deposit> findByIdAndAccountId(Long billId, Long accountId) {
        Optional<Deposit> optionalDeposit = depositRepository.findById(billId);
        if (optionalDeposit.isPresent()){
            Deposit deposit = optionalDeposit.get();
            if (deposit.getBill().getAccount().getId().equals(accountId)) {
                return optionalDeposit;
            }
        }

        return Optional.empty();
    }
}