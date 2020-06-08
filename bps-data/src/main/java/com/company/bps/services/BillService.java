package com.company.bps.services;

import com.company.bps.model.Bill;

import java.util.Optional;
import java.util.Set;

public interface BillService extends CrudService<Bill, Long> {

    Set<Bill> findAllByAccountId(Long accountId);

    Optional<Bill> findByIdAndAccountId(Long id, Long accountId);

    Optional<String> send(Long senderBillId,Long recipientBillNumber, Double amount);

    Optional<String> transfer(Long senderBillId, Long recipientBillId, Double amount);

    void create(Long accountId);
}