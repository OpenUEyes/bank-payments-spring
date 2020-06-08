package com.company.bps.controllers;

import com.company.bps.model.Account;
import com.company.bps.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final AccountService accountService;

    public RegistrationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/registration", "/registration.html"})
    public String toRegistration(Model model) {
        model.addAttribute("account", new Account());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute("account") Account account, BindingResult bindingResult) {

        boolean exist = accountService.existsByLoginOrEmailOrPhoneNumber(account.getLogin(), account.getEmail(), account.getPhoneNumber());
        if (exist) {
            String errorMessage = "Login or Email or Phone Number are already in use.";
            model.addAttribute("errorMessage", errorMessage);
            return "registration";
        }

        try {
            accountService.save(account);
            return "registration_success";

        } catch (Exception e) {
            return "error";
        }
    }
}