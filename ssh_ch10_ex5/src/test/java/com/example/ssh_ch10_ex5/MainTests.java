package com.example.ssh_ch10_ex5;

import com.example.ssh_ch10_ex5.exceptions.NotEnoughMoneyException;
import com.example.ssh_ch10_ex5.model.PaymentDetails;
import com.example.ssh_ch10_ex5.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MainTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @DisplayName(
            """
            GIVEN
                the payment service returns a payment details object
            WHEN
                the /payment endpoint is called
            THEN
                the response status is 202 Accepted and the response body contains the payment details
            """
    )
    void makePaymentAccepted() throws Exception {
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAmount(100.0);

        given(paymentService.processPayment()).willReturn(paymentDetails);

        mockMvc.perform(post("/payment"))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    @DisplayName(
            """
            GIVEN
                the payment service throws a NotEnoughMoneyException
            WHEN
                the /payment endpoint is called
            THEN
                the response status is 400 Bad Request and the response body contains the error message
            """
    )
    void makePaymentBadRequest() throws Exception {
        given(paymentService.processPayment()).willThrow(new NotEnoughMoneyException());

        mockMvc.perform(post("/payment"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough money to make the payment."));
    }

}
