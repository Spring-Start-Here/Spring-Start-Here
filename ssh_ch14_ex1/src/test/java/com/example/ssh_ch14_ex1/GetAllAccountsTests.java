package com.example.ssh_ch14_ex1;

import com.example.ssh_ch14_ex1.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class GetAllAccountsTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("""
            GIVEN The database contains some accounts.
            WHEN The get all accounts endpoint is called without a name parameter.
            THEN The response status is OK and all accounts are returned.
            """)
    void getAllAccounts() throws Exception {
        given(accountRepository.findAll()).willReturn(List.of());

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk());

        verify(accountRepository).findAll();
    }

    @Test
    @DisplayName("""
            GIVEN The database contains accounts with a specific name.
            WHEN The get all accounts endpoint is called with a name parameter.
            THEN The response status is OK and accounts with that name are returned.
            """)
    void findAccountsByName() throws Exception {
        String name = "John";
        given(accountRepository.findAccountsByName(name)).willReturn(List.of());

        mockMvc.perform(get("/accounts")
                .param("name", name))
                .andExpect(status().isOk());

        verify(accountRepository).findAccountsByName(name);
    }
}
