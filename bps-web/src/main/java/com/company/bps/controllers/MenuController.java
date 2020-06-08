package com.company.bps.controllers;

import com.company.bps.model.Bill;
import com.company.bps.services.BillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Set;

@SessionAttributes("accountId")
@RequestMapping("/bills")
@Controller
public class MenuController {

    private final BillService billService;

    public MenuController(BillService billService) {
        this.billService = billService;
    }

    @RequestMapping({"/", ""})
    public String getBills(Model model, RedirectAttributes redirectAttributes) {
        if (model.getAttribute("accountId") == null) {
            model.addAttribute("accountId", redirectAttributes.getAttribute("accountId"));
        }

        final long accountId = (long) model.getAttribute("accountId");
        Set<Bill> bills = billService.findAllByAccountId(accountId);
        model.addAttribute("bills", bills);
        return "bills";
    }

    @PostMapping("/send")
    public String send(Model model, @RequestParam Long senderBillId, @RequestParam String recipientBillNumber, @RequestParam String amount) {
        final Double validatedAmount;
        final Long validatedRecipientBillNumber;

        try {
            validatedAmount = Double.parseDouble(amount);
            validatedRecipientBillNumber = Long.parseLong(recipientBillNumber);
        } catch (NullPointerException | NumberFormatException e){
            model.addAttribute("errorMessage" , "Invalid input!");
            return "forward:/bills";
        }

        Optional<String> errorMessage = billService.send(senderBillId, validatedRecipientBillNumber, validatedAmount);
        if (errorMessage.isPresent()) {
            model.addAttribute("errorMessage", errorMessage.get());
            return "forward:/bills";
        }
        return "bills_success";
    }

    @PostMapping("/transfer")
    public String transfer(Model model, @RequestParam Long senderBillId, @RequestParam Long recipientBillId, @RequestParam String amount) {
        final Double validatedAmount;

        try {
            validatedAmount = Double.parseDouble(amount);
        } catch (NullPointerException | NumberFormatException e){
            model.addAttribute("errorMessage" , "Invalid input!");
            return "forward:/bills";
        }


        Optional<String> errorMessage = billService.transfer(senderBillId, recipientBillId, validatedAmount);
        if (errorMessage.isPresent()) {
            model.addAttribute("errorMessage", errorMessage.get());
            return "forward:/bills/";
        }
        return "bills_success";
    }

    @PostMapping("/new")
    public String create(Model model) {
        final long accountId = (long) model.getAttribute("accountId");

        try {
            billService.create(accountId);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Service is temporarily unavailable. Please try again later.");
        }
        return "forward:/bills";
    }
}