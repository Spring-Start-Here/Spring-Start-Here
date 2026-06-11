package com.example.ssh_ch14_ex1;

import com.example.ssh_ch14_ex1.exceptions.AccountNotFoundException;
import com.example.ssh_ch14_ex1.model.Account;
import com.example.ssh_ch14_ex1.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TransferMoneyTests {

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
            GIVEN Two accounts exist with IDs 1 and 2, and both have 1000 balance.
            WHEN A transfer of 100 is requested from account 1 to account 2.
            THEN The response status is OK and both accounts' balances are updated correctly.
            """)
    void transferMoneySuccess() throws Exception {
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("1000"));

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setAmount(new BigDecimal("1000"));

        given(accountRepository.findById(1L)).willReturn(Optional.of(sender));
        given(accountRepository.findById(2L)).willReturn(Optional.of(receiver));

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "senderAccountId": 1,
                            "receiverAccountId": 2,
                            "amount": 100
                        }
                        """))
                .andExpect(status().isOk());

        verify(accountRepository).changeAmount(1L, new BigDecimal("900"));
        verify(accountRepository).changeAmount(2L, new BigDecimal("1100"));
    }

    @Test
    @DisplayName("""
            GIVEN The sender account with ID 1 does not exist.
            WHEN A transfer of 100 is requested from account 1 to account 2.
            THEN An AccountNotFoundException is thrown.
            """)
    void transferMoneySenderNotFound() {
        given(accountRepository.findById(1L)).willReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "senderAccountId": 1,
                                    "receiverAccountId": 2,
                                    "amount": 100
                                }
                                """)));

        assertTrue(exception.getCause() instanceof AccountNotFoundException);
    }

    @Test
    @DisplayName("""
            GIVEN The sender account exists but the receiver account with ID 2 does not exist.
            WHEN A transfer of 100 is requested from account 1 to account 2.
            THEN An AccountNotFoundException is thrown.
            """)
    void transferMoneyReceiverNotFound() {
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("1000"));

        given(accountRepository.findById(1L)).willReturn(Optional.of(sender));
        given(accountRepository.findById(2L)).willReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () ->
                mockMvc.perform(post("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "senderAccountId": 1,
                                    "receiverAccountId": 2,
                                    "amount": 100
                                }
                                """)));

        assertTrue(exception.getCause() instanceof AccountNotFoundException);
    }

}
