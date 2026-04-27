package com.example.ssh_ch9_ex3.controllers;


import com.example.ssh_ch9_ex3.processors.LoginProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class LoginControllerLoginPostTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private LoginProcessor loginProcessor;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("""
            GIVEN a POST request with valid credentials
            WHEN the loginPost method is called and login succeeds
            THEN it should redirect to /main
            """)
    void testLoginPostSuccess() throws Exception {
        when(loginProcessor.login()).thenReturn(true);

        mockMvc.perform(post("/")
                        .param("username", "testuser")
                        .param("password", "testpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/main"));

        verify(loginProcessor).setUsername("testuser");
        verify(loginProcessor).setPassword("testpass");
        verify(loginProcessor).login();
    }

    @Test
    @DisplayName("""
            GIVEN a POST request with invalid credentials
            WHEN the loginPost method is called and login fails
            THEN it should return status 200, the login.html view, and failure message
            """)
    void testLoginPostFailure() throws Exception {
        when(loginProcessor.login()).thenReturn(false);

        mockMvc.perform(post("/")
                        .param("username", "wronguser")
                        .param("password", "wrongpass"))
                .andExpect(status().isOk())
                .andExpect(view().name("login.html"))
                .andExpect(model().attribute("message", "Login failed!"));

        verify(loginProcessor).setUsername("wronguser");
        verify(loginProcessor).setPassword("wrongpass");
        verify(loginProcessor).login();
    }
}
