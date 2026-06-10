package com.example.ssh_ch13_ex1;

import com.example.ssh_ch13_ex1.model.Account;
import com.example.ssh_ch13_ex1.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GetAccountsTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @DisplayName(
            """
            GIVEN a list of accounts exists in the repository
            WHEN the GET /accounts endpoint is called
            THEN the response status should be 200 OK
            AND the response should contain the list of accounts
            """
    )
    void getAllAccountsTest() throws Exception {
        Account account = new Account();
        account.setId(1);
        account.setName("John Doe");
        account.setAmount(new BigDecimal("1000"));

        List<Account> accounts = List.of(account);

        given(accountRepository.findAllAccounts())
                .willReturn(accounts);

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].amount").value(1000));
    }

    @Test
    @DisplayName(
            """
            GIVEN no accounts exist in the repository
            WHEN the GET /accounts endpoint is called
            THEN the response status should be 200 OK
            AND the response should contain an empty list
            """
    )
    void getAllAccountsEmptyTest() throws Exception {
        given(accountRepository.findAllAccounts())
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
