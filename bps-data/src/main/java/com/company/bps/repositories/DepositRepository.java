package com.company.bps.repositories;

import com.company.bps.model.Credit;
import com.company.bps.model.Deposit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepositRepository extends CrudRepository<Deposit, Long> {
//    Optional<Deposit> findByIdAndAccountId();
}