package com.example.ssh_ch9_ex1.processors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LoginProcessorLoginTest {

    @Autowired
    private LoginProcessor loginProcessor;

    @Test
    @DisplayName("""
            GIVEN valid username 'natalie' and valid password 'password'
            WHEN login is called
            THEN return true
            """)
    void testLoginWithValidCredentials() {
        loginProcessor.setUsername("natalie");
        loginProcessor.setPassword("password");

        boolean result = loginProcessor.login();

        assertTrue(result);
    }

    @Test
    @DisplayName("""
            GIVEN invalid username 'john' and valid password 'password'
            WHEN login is called
            THEN return false
            """)
    void testLoginWithInvalidUsername() {
        loginProcessor.setUsername("john");
        loginProcessor.setPassword("password");

        boolean result = loginProcessor.login();

        assertFalse(result);
    }

    @Test
    @DisplayName("""
            GIVEN valid username 'natalie' and invalid password 'wrongpassword'
            WHEN login is called
            THEN return false
            """)
    void testLoginWithInvalidPassword() {
        loginProcessor.setUsername("natalie");
        loginProcessor.setPassword("wrongpassword");

        boolean result = loginProcessor.login();

        assertFalse(result);
    }

    @Test
    @DisplayName("""
            GIVEN invalid username 'john' and invalid password 'wrongpassword'
            WHEN login is called
            THEN return false
            """)
    void testLoginWithInvalidCredentials() {
        loginProcessor.setUsername("john");
        loginProcessor.setPassword("wrongpassword");

        boolean result = loginProcessor.login();

        assertFalse(result);
    }

    @Test
    @DisplayName("""
            GIVEN null username and valid password 'password'
            WHEN login is called
            THEN return false
            """)
    void testLoginWithNullUsername() {
        loginProcessor.setUsername(null);
        loginProcessor.setPassword("password");

        boolean result = loginProcessor.login();

        assertFalse(result);
    }

    @Test
    @DisplayName("""
            GIVEN valid username 'natalie' and null password
            WHEN login is called
            THEN return false
            """)
    void testLoginWithNullPassword() {
        loginProcessor.setUsername("natalie");
        loginProcessor.setPassword(null);

        boolean result = loginProcessor.login();

        assertFalse(result);
    }
}
