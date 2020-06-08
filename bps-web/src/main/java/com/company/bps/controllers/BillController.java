package com.company.bps.controllers;

import com.company.bps.model.Bill;
import com.company.bps.model.Credit;
import com.company.bps.model.Deposit;
import com.company.bps.model.Type;
import com.company.bps.services.BillService;
import com.company.bps.services.CreditService;
import com.company.bps.services.DepositService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@SessionAttributes("accountId")
@RequestMapping("/bill")
@Controller
public class BillController {

    private final BillService billService;
    private final CreditService creditService;
    private final DepositService depositService;

    public BillController(BillService billService, CreditService creditService, DepositService depositService) {
        this.billService = billService;
        this.creditService = creditService;
        this.depositService = depositService;
    }

    @PostMapping("/get")
    public String get(Model model, @RequestParam Long billId) {
        final long accountId = (long) model.getAttribute("accountId");
        Optional<Bill> optionalBill = billService.findByIdAndAccountId(billId, accountId);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            model.addAttribute("bill", bill);

            Type type = bill.getType();
            if (Type.CREDIT == type) {
                Optional<Credit> optionalCredit = creditService.findByIdAndAccountId(billId, accountId);
                if (optionalCredit.isPresent()) {
                    Credit credit = optionalCredit.get();
                    model.addAttribute("credit", credit);
                } else {
                    log.error("Account id:" + accountId + "; Bill id:" + billId
                            + "; BILL have type:CREDIT, but not attached CREDIT with same id as BILL id present!"
                            + "Or query with wrong input ACCOUNT.id and BILL.id");
                    return "error";
                }

            } else if (Type.DEPOSIT == type) {
                Optional<Deposit> optionalDeposit = depositService.findByIdAndAccountId(billId, accountId);
                if (optionalDeposit.isPresent()) {
                    Deposit deposit = optionalDeposit.get();
                    model.addAttribute("deposit", deposit);
                } else {
                    log.error("Account id:" + accountId + "; Bill id:" + billId
                            + "; BILL have type:DEPOSIT, but not attached DEPOSIT with same id as BILL id present!"
                            + "Or query with wrong input ACCOUNT.id and BILL.id");
                    return "error";
                }
            }
//            model.addAttribute("billId", billId);
            return "bill";
        } else {
            return "error";
        }
    }

    @PostMapping("/new/credit")
    public String newCredit(Model model, HttpServletRequest request, @RequestParam Long billId, @RequestParam String amount) {
        final Double validatedAmount;

        try {
            validatedAmount = Double.parseDouble(amount);
        } catch (NullPointerException | NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid input!");
            request.setAttribute("billId", billId);
            return "forward:/bill/get";
        }

        Double limit = 10000.0;
        if (limit < validatedAmount) {
            model.addAttribute("errorMessage", "Amount: " + validatedAmount + " should be less or equal limit: " + limit);
            request.setAttribute("billId", billId);
            return "forward:/bill/get";
        }

        Bill bill = billService.findById(billId);
        Credit credit = Credit.builder()
                .bill(bill)
                .debt(validatedAmount)
                .limit(limit)
                .percentage(23.0)
                .start(LocalDate.now())
                .deadline(LocalDate.now().plusYears(1))
                .build();

        bill.setType(Type.CREDIT);
        bill.setBalance(bill.getBalance() + validatedAmount);

        creditService.save(credit);

        model.addAttribute("billId", billId);
        return "bill_success";
    }


    @PostMapping("/new/deposit")
    public String newDeposit(Model model, HttpServletRequest request, @RequestParam Long billId, @RequestParam String amount) {
        final Double validatedAmount;

        try {
            validatedAmount = Double.parseDouble(amount);
        } catch (NullPointerException | NumberFormatException e) {
            model.addAttribute("errorMessage", "Invalid input!");
            request.setAttribute("billId", billId);
            return "forward:/bill/get";
        }

        Bill bill = billService.findById(billId);
        double recipientBalance = bill.getBalance();

        if (recipientBalance < validatedAmount) {
            model.addAttribute("errorMessage", "Your balance is: " + recipientBalance + ", you can't put to deposit more than your balance");
            request.setAttribute("billId", billId);
            return "forward:/bill/get";
        }

        Deposit deposit = Deposit.builder()
                .bill(bill)
                .amount(validatedAmount)
                .rate(1.2)
                .start(LocalDate.now())
                .finish(LocalDate.now().plusYears(1))
                .build();

        bill.setType(Type.DEPOSIT);
        bill.setBalance(bill.getBalance() - validatedAmount);

        depositService.save(deposit);

        model.addAttribute("billId", billId);
        return "bill_success";
    }
}