package com.example;

import com.example.beans.Parrot;
import com.example.config.ProjectConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ProjectConfig.class })
public class AppTests {

    @Autowired
    private Parrot parrot;

    @Autowired
    private String hello;

    @Autowired
    private Integer ten;

    @Test
    @DisplayName("Test that the parrot 'Koko' is in the Spring context")
    void testKokoIsInContext() {
        assertEquals("Koko", parrot.getName());
    }

    @Test
    @DisplayName("Test that the String 'Hello' is in the Spring context")
    void testHelloIsInContext() {
        assertEquals("Hello", hello);
    }

    @Test
    @DisplayName("Test that the Integer 10 is in the Spring context")
    void testTenIsInContext() {
        assertEquals(10, ten);
    }
}
