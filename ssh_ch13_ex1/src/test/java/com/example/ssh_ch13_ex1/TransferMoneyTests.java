package com.example.ssh_ch13_ex1;

import com.example.ssh_ch13_ex1.model.Account;
import com.example.ssh_ch13_ex1.repositories.AccountRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransferMoneyTests {

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
            GIVEN two accounts exist in the repository
            WHEN the POST /transfer endpoint is called with a valid transfer request
            THEN the response status should be 200 OK
            AND the amounts for both accounts should be updated correctly in the repository
            """
    )
    void transferMoneySuccessTest() throws Exception {
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("1000"));

        Account receiver = new Account();
        receiver.setId(2);
        receiver.setAmount(new BigDecimal("1000"));

        given(accountRepository.findAccountById(1))
                .willReturn(sender);
        given(accountRepository.findAccountById(2))
                .willReturn(receiver);

        String body = """
                {
                    "senderAccountId": 1,
                    "receiverAccountId": 2,
                    "amount": 100
                }
                """;

        mockMvc.perform(
                        post("/transfer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body)
                )
                .andExpect(status().isOk());

        verify(accountRepository).changeAmount(1, new BigDecimal("900"));
        verify(accountRepository).changeAmount(2, new BigDecimal("1100"));
    }

    @Test
    @DisplayName(
            """
            GIVEN the sender account does not exist in the repository
            WHEN the POST /transfer endpoint is called
            THEN an exception should be thrown
            AND no amount should be changed in the repository
            """
    )
    void transferMoneySenderNotFoundTest() throws Exception {
        given(accountRepository.findAccountById(1))
                .willThrow(new RuntimeException("Account not found"));

        String body = """
                {
                    "senderAccountId": 1,
                    "receiverAccountId": 2,
                    "amount": 100
                }
                """;

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(
                                post("/transfer")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(body)
                        )
        );

        verify(accountRepository, never()).changeAmount(anyLong(), any());
    }

    @Test
    @DisplayName(
            """
            GIVEN the receiver account does not exist in the repository
            WHEN the POST /transfer endpoint is called
            THEN an exception should be thrown
            AND no amount should be changed in the repository
            """
    )
    void transferMoneyReceiverNotFoundTest() throws Exception {
        Account sender = new Account();
        sender.setId(1);
        sender.setAmount(new BigDecimal("1000"));

        given(accountRepository.findAccountById(1))
                .willReturn(sender);
        given(accountRepository.findAccountById(2))
                .willThrow(new RuntimeException("Account not found"));

        String body = """
                {
                    "senderAccountId": 1,
                    "receiverAccountId": 2,
                    "amount": 100
                }
                """;

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () ->
                mockMvc.perform(
                                post("/transfer")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(body)
                        )
        );

        verify(accountRepository, never()).changeAmount(anyLong(), any());
    }
}
