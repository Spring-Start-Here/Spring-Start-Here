package com.example.ssh_ch10_ex6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MainTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Test
    @DisplayName("""
            GIVEN
                The payment service throws an exception
            WHEN
                A POST request is sent to /payment
            THEN
                The response status is 400 Bad Request
                The response body contains the error message
            """)
    void testPaymentFails() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mockMvc.perform(post("/payment"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Not enough money to make the payment."));
    }

}
