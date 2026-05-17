package com.example.ssh_ch10_openapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MainTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName("""
            GIVEN
                A payment request with an amount
            WHEN
                The makePayment endpoint is called
            THEN
                The response status is ACCEPTED
                The response body contains the same amount
            """)
    void makePaymentTest() throws Exception {
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "amount": 100.5
                                }
                                """))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.amount").value(100.5));
    }

}
