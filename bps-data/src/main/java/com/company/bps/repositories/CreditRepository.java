package com.company.bps.repositories;

import com.company.bps.model.Credit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CreditRepository extends CrudRepository<Credit, Long> {

//    @Query("SELECT c FROM credit c JOIN bill b ON c.id = b.id WHERE c.id = :billId AND b.account_id = :accountId")
//    Credit findByIdAndAccountId(@Param("billId") Long billId, @Param("accountId") Long accountId);
}