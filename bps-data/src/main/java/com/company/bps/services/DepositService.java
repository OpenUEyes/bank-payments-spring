package com.company.bps.services;

import com.company.bps.model.Credit;
import com.company.bps.model.Deposit;

import java.util.Optional;

public interface DepositService extends CrudService<Deposit, Long> {

    Optional<Deposit> findByIdAndAccountId(Long billId, Long accountId);
}