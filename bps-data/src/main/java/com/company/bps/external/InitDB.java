package com.company.bps.external;

import com.company.bps.model.*;
import com.company.bps.services.AccountService;
import com.company.bps.services.BillService;
import com.company.bps.services.CreditService;
import com.company.bps.services.DepositService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitDB implements CommandLineRunner {

    private final AccountService accountService;
    private final BillService billService;
    private final CreditService creditService;
    private final DepositService depositService;

    public InitDB(AccountService accountService, BillService billService, CreditService creditService, DepositService depositService) {
        this.accountService = accountService;
        this.billService = billService;
        this.creditService = creditService;
        this.depositService = depositService;
    }

    @Override
    public void run(String... args) {
        if (accountService.findAll().size() == 0) {
            loadData();
        }
    }

    private void loadData() {
        Account account = Account.builder()
                .login("LoginAdmin")
                .password("PasswordAdmin")
                .email("EmailAdmin@gamil.com")
                .name("Roman")
                .surname("Romanenko")
                .phoneNumber("0981773984")
                .build();

        Bill bill1 = Bill.builder().balance(1000.0).type(Type.CREDIT).validity(LocalDate.now().plusYears(3)).account(account).build();
        Bill bill2 = Bill.builder().balance(100.0).type(Type.CREDIT).validity(LocalDate.now().plusYears(3)).account(account).build();
        Bill bill3 = Bill.builder().balance(0.0).type(Type.UNSIGNED).validity(LocalDate.now().plusYears(3)).account(account).build();
        Bill bill4 = Bill.builder().balance(0.0).type(Type.DEPOSIT).validity(LocalDate.now().plusYears(3)).account(account).build();
        Bill bill5 = Bill.builder().balance(10000.0).type(Type.UNSIGNED).validity(LocalDate.now().plusYears(3)).account(account).build();

        accountService.save(account);

        Bill savedBill1 = billService.save(bill1);
        Bill savedBill2 = billService.save(bill2);
        Bill savedBill3 = billService.save(bill3);
        Bill savedBill4 = billService.save(bill4);
        Bill savedBill5 = billService.save(bill5);


        Credit credit1 = Credit.builder().bill(savedBill1).debt(500.0).percentage(1.3).limit(10000.0).start(LocalDate.now()).deadline(LocalDate.now().plusYears(1)).build();
        Credit credit2 = Credit.builder().bill(savedBill2).debt(100.0).percentage(1.3).limit(10000.0).start(LocalDate.now()).deadline(LocalDate.now().plusYears(1)).build();

        Deposit deposit1 = Deposit.builder().bill(savedBill4).amount(500.0).rate(1.4).start(LocalDate.now()).finish(LocalDate.now().plusYears(1)).build();

        creditService.save(credit1);
        creditService.save(credit2);
        depositService.save(deposit1);
    }
}