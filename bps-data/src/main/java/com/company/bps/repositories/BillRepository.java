package com.company.bps.repositories;

import com.company.bps.model.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface BillRepository extends CrudRepository<Bill, Long> {

//    Set<Bill> findAllByAccountId(Long account_id);

    Optional<Bill> findByIdAndAccountId(Long id, Long account_id);

    Set<Bill> findAllByAccountId(Long account_id);
}