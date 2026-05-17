package com.example.ssh_ch10_ex7;

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
    @DisplayName("Test making a payment returns 202 Accepted and the same amount in the response body.")
    void makePaymentTest() throws Exception {
        // GIVEN
        String body = """
                {
                    "amount": 1000.0
                }
                """;

        // WHEN
        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                // THEN
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.amount").value(1000.0));
    }

}
