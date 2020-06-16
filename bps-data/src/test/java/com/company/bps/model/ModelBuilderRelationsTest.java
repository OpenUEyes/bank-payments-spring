package com.company.bps.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelBuilderRelationsTest {

    @Test
    void accountRelatedToBill() {
        Account account = Account.builder()
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .name("longName")
                .surname("surname")
                .phoneNumber("0981773984")
                .build();

        Bill bill = Bill.builder()
                .account(account)
                .type(Type.UNSIGNED)
                .balance(1000.0)
                .validity(LocalDate.now().plusYears(2))
                .build();

        assertTrue(account.getBills().contains(bill));
    }

    @Test
    void billRelatedToAccount() {
        Bill bill = Bill.builder()
                .type(Type.UNSIGNED)
                .balance(1000.0)
                .validity(LocalDate.now().plusYears(2))
                .build();
        Set<Bill> billSet = new HashSet<>();
        billSet.add(bill);

        Account account = Account.builder()
                .bills(billSet)
                .login("login")
                .password("password")
                .email("email@gmail.com")
                .name("longName")
                .surname("surname")
                .phoneNumber("0981773984")
                .build();

        assertEquals(account, bill.getAccount());
    }

    @Test
    void billRelatedToCredit() {
        Bill bill = Bill.builder()
                .type(Type.UNSIGNED)
                .balance(1000.0)
                .validity(LocalDate.now().plusYears(2))
                .build();

        Credit credit = Credit.builder()
                .bill(bill)
                .debt(500.0)
                .percentage(1.3)
                .limit(10000.0)
                .start(LocalDate.now())
                .deadline(LocalDate.now().plusYears(1))
                .build();

        assertEquals(credit, bill.getCredit());
    }

    @Test
    void billRelatedToDeposit() {
        Bill bill = Bill.builder()
                .type(Type.UNSIGNED)
                .balance(1000.0)
                .validity(LocalDate.now().plusYears(2))
                .build();

        Deposit deposit = Deposit.builder()
                .bill(bill)
                .amount(500.0)
                .rate(1.4)
                .start(LocalDate.now())
                .finish(LocalDate.now().plusYears(1))
                .build();

        assertEquals(deposit, bill.getDeposit());
    }
}