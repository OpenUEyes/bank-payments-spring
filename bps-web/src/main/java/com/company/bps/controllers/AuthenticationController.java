package com.company.bps.controllers;

import com.company.bps.model.Account;
import com.company.bps.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

//@SessionAttributes("accountId")
@Controller
public class AuthenticationController {

    private final AccountService accountService;

    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping({"/", "/authentication", "/authentication.html"})
    public String toAuthentication() {
        return "index";
    }

    @PostMapping("/authentication")
    public String authentication(Model model, RedirectAttributes redirectAttributes, @RequestParam String login, @RequestParam String password) {
        Optional<Account> resultedAccount = accountService.findByLoginAndPassword(login, password);
        if (resultedAccount.isPresent()) {
            Account account = resultedAccount.get();
            redirectAttributes.addFlashAttribute("accountId", account.getId());
//            model.addAttribute("bills", account.getBills());
            return "redirect:/bills";
        } else {
            final String errorMessage = "Wrong login and password!";
            model.addAttribute("errorMessage", errorMessage);
            return "index";
        }
    }

//    @PostMapping("/next")
//    public String fillNext(Model model) {
//        final long account_id = (long) model.getAttribute("account_id");
//        Account account = accountService.findById(account_id);
//        model.addAttribute("bills", account.getBills());
//        return "bills";
//    }

//    @PostMapping
//    public Long setSessionId(@ModelAttribute("account_id") Long account_id) {
////        return (Long) model.getAttribute("account_id");
//        return account_id;
//    }
}