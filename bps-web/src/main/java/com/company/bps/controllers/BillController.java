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
            return "bill";
        } else {
            return "error";
        }
    }
}