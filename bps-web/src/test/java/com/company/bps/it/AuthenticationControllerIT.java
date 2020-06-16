package com.company.bps.it;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void authenticationShouldReturnMenu() throws Exception {
        final String correctLogin = "LoginAdmin";
        final String correctPassword = "PasswordAdmin";

        mockMvc.perform(post("/authentication")
                .param("login", correctLogin)
                .param("password", correctPassword))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/bills"))
                .andExpect(flash().attributeExists("accountId"));
    }

    @Test
    void authenticationShouldReturnErrorMessage() throws Exception {
        mockMvc.perform(post("/authentication")
                .param("login", "wrongLogin")
                .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}