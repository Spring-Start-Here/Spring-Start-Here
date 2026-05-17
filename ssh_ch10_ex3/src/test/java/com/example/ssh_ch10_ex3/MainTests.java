package com.example.ssh_ch10_ex3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
            GIVEN the endpoint /france is available
            WHEN calling the /france endpoint
            THEN the response status is 200 OK and the body contains France country details
            """)
    void franceTest() throws Exception {
        mockMvc.perform(get("/france"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("France"))
                .andExpect(jsonPath("$.population").value(67));
    }

    @Test
    @DisplayName("""
            GIVEN the endpoint /all is available
            WHEN calling the /all endpoint
            THEN the response status is 200 OK and the body contains a list of countries
            """)
    void allCountriesTest() throws Exception {
        mockMvc.perform(get("/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("France"))
                .andExpect(jsonPath("$[0].population").value(67))
                .andExpect(jsonPath("$[1].name").value("Spain"))
                .andExpect(jsonPath("$[1].population").value(47));
    }

}
