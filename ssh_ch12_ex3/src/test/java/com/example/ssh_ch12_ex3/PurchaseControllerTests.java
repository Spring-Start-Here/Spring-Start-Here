package com.example.ssh_ch12_ex3;

import com.example.ssh_ch12_ex3.controllers.PurchaseController;
import com.example.ssh_ch12_ex3.model.Purchase;
import com.example.ssh_ch12_ex3.repositories.PurchaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PurchaseControllerTests {

    private PurchaseRepository purchaseRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        purchaseRepository = Mockito.mock(PurchaseRepository.class);
        PurchaseController purchaseController = new PurchaseController(purchaseRepository);
        this.mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
    }

    @Test
    @DisplayName(
            """
            GIVEN a purchase to store
            WHEN the POST /purchase endpoint is called
            THEN the status should be 200 OK
            AND the purchase should be saved in the database
            """
    )
    void storePurchaseTest() throws Exception {
        String json = """
                {
                    "product": "T-Shirt",
                    "price": 15.00
                }
                """;

        mockMvc.perform(post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(purchaseRepository).storePurchase(any(Purchase.class));
    }

    @Test
    @DisplayName(
            """
            GIVEN existing purchases in the database
            WHEN the GET /purchase endpoint is called
            THEN the status should be 200 OK
            AND the response should contain all the purchases from the database
            """
    )
    void findPurchasesTest() throws Exception {
        Purchase p = new Purchase();
        p.setProduct("Jeans");
        p.setPrice(new BigDecimal("45.00"));

        when(purchaseRepository.findAllPurchases()).thenReturn(List.of(p));

        mockMvc.perform(get("/purchase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].product").value("Jeans"))
                .andExpect(jsonPath("$[0].price").value(45.0));
    }

}
