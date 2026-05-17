package com.example.ssh_ch10_ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MainTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("""
            GIVEN the hello endpoint is available,
            WHEN a GET request is sent to /hello,
            THEN the response status should be OK and the body should contain 'Hello!'
            """)
    void helloEndpointTest() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"));
    }

    @Test
    @DisplayName("""
            GIVEN the ciao endpoint is available,
            WHEN a GET request is sent to /ciao,
            THEN the response status should be OK and the body should contain 'Ciao!'
            """)
    void ciaoEndpointTest() throws Exception {
        mockMvc.perform(get("/ciao"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ciao!"));
    }

}
