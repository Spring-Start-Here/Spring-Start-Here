package com.example.ssh_ch12_ex1;

import com.example.ssh_ch12_ex1.controllers.PurchaseController;
import com.example.ssh_ch12_ex1.model.Purchase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ssh_ch12_ex1.repositories.PurchaseRepository;
import java.util.List;

@SpringBootTest
class PurchaseControllerTests {

    @Autowired
    private PurchaseController purchaseController;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
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

        List<Purchase> purchases = purchaseRepository.findAllPurchases();
        assertFalse(purchases.isEmpty());
        assertEquals("T-Shirt", purchases.get(purchases.size() - 1).getProduct());
        assertEquals(0, new BigDecimal("15.00").compareTo(purchases.get(purchases.size() - 1).getPrice()));
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
        purchaseRepository.storePurchase(p);

        mockMvc.perform(get("/purchase"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.product == 'Jeans')].price").value(45.0));
    }

}
