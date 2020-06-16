package com.company.bps.controllers;

import com.company.bps.model.Account;
import com.company.bps.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountServiceMock;

    @Test
    void shouldReturnIndex() throws Exception {
        mockMvc.perform(get("/authentication"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verifyNoInteractions(accountServiceMock);
    }

    @Test
    void authenticationShouldReturnMenu() throws Exception {
        Account account = Account.builder().id(1L).build();
        final String correctLogin = "LoginAdmin";
        final String correctPassword = "PasswordAdmin";
        when(accountServiceMock.findByLoginAndPassword(correctLogin, correctPassword)).thenReturn(Optional.of(account));

        mockMvc.perform(post("/authentication")
                .param("login", correctLogin)
                .param("password", correctPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bills"))
                .andExpect(flash().attributeExists("accountId"));
    }

    @Test
    void authenticationShouldReturnErrorMessage() throws Exception {
        when(accountServiceMock.findByLoginAndPassword(any(), any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/authentication")
                .param("login", "wrongLogin")
                .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}