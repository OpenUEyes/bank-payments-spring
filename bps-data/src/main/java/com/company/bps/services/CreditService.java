package com.company.bps.services;

import com.company.bps.model.Credit;

import java.util.Optional;

public interface CreditService extends CrudService<Credit, Long> {

    Optional<Credit> findByIdAndAccountId(Long billId, Long accountId);

}