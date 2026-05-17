package com.example.ssh_ch10_ex4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
            GIVEN the application is running
            WHEN the /france endpoint is called
            THEN the response status should be 202 Accepted and the body should contain France
            """)
    void testFranceEndpoint() throws Exception {
        mockMvc.perform(get("/france"))
                .andExpect(status().isAccepted())
                .andExpect(header().string("continent", "Europe"))
                .andExpect(header().string("capital", "Paris"))
                .andExpect(header().string("favorite_food", "cheese and wine"))
                .andExpect(jsonPath("$.name").value("France"))
                .andExpect(jsonPath("$.population").value(67));
    }

}
