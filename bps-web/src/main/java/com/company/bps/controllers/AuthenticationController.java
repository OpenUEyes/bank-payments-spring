package com.company.bps.controllers;

import com.company.bps.model.Account;
import com.company.bps.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthenticationController {

    private final AccountService accountService;

    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/",  "/authentication", "/authentication.html"})
    public String toAuthentication() {
        return "index";
    }

    @PostMapping("/authentication")
    public String authentication(Model model, @RequestParam String login, @RequestParam String password) {
        Optional<Account> resultedAccount = accountService.findByLoginAndPassword(login, password);
        if (resultedAccount.isPresent()) {
            return "bills";
        } else {
            final String errorMessage = "Wrong login and password!";
            model.addAttribute("errorMessage", errorMessage);
            return "index";
        }
    }
}