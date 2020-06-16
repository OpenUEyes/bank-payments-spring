package com.company.bps.services;

import com.company.bps.model.Account;
import com.company.bps.model.Bill;
import com.company.bps.model.Type;
import com.company.bps.repositories.AccountRepository;
import com.company.bps.repositories.BillRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final AccountRepository accountRepository;

    public BillServiceImpl(BillRepository billRepository, AccountRepository accountRepository) {
        this.billRepository = billRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Bill> findAll() {
        Set<Bill> bills = new HashSet<>();
        billRepository.findAll().forEach(bills::add);
        return bills;
    }

    @Override
    public void deleteById(Long id) {
        billRepository.deleteById(id);
    }

    @Override
    public void delete(Bill bill) {
        billRepository.delete(bill);
    }

//    @Override
//    public Set<Bill> findAllByAccountId(Long account_id) {
//        return repository.findAllByAccountId(account_id);
//    }

    @Override
    public Optional<Bill> findByIdAndAccountId(Long id, Long accountId) {
        return billRepository.findByIdAndAccountId(id, accountId);
    }

    @Override
    @Transactional
    public Optional<String> send(Long senderBillId, Long recipientBillNumber, Double amount) {
        Bill senderBill = billRepository.findById(senderBillId).orElseThrow();

        double senderBalance = senderBill.getBalance();
        if (senderBalance < amount) {
            return Optional.of("Not enough money! You have " + senderBalance + " USD but need " + amount + " USD!!");
        }
        senderBill.setBalance(senderBalance - amount);
        // external operations
        // example: connection to another bank to send money - recipientBillNumber
        billRepository.save(senderBill);

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<String> transfer(Long senderBillId, Long recipientBillId, Double amount) {
        Bill senderBill = billRepository.findById(senderBillId).orElseThrow();
        Bill recipientBill = billRepository.findById(recipientBillId).orElseThrow();

        double senderBalance = senderBill.getBalance();
        if (senderBalance < amount) {
            return Optional.of("Not enough money! You have " + senderBalance + " USD but need " + amount + " USD!!");
        }
        senderBill.setBalance(senderBalance - amount);
        recipientBill.setBalance(recipientBill.getBalance() + amount);
        billRepository.save(senderBill);
        billRepository.save(recipientBill);

        return Optional.empty();
    }

    @Override
    public void create(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow();

        final int billValidityYearsPeriod = 4;
        Bill bill = Bill.builder()
                .type(Type.UNSIGNED)
                .balance(0.0)
                .validity(LocalDate.now().plusYears(billValidityYearsPeriod))
                .account(account) // auto linking
                .build();

        billRepository.save(bill);
    }

    @Override
    public Set<Bill> findAllByAccountId(Long accountId) {
        return billRepository.findAllByAccountId(accountId);
    }
}