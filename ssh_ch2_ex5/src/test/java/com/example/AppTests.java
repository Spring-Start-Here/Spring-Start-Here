package com.example;

import com.example.beans.Parrot;
import com.example.config.ProjectConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTests {

    private AnnotationConfigApplicationContext context;

    @BeforeEach
    void init() {
        context = new AnnotationConfigApplicationContext(ProjectConfig.class);
    }

    @Test
    @DisplayName("Test that the parrot 'Koko' is in the Spring context")
    void testKokoIsInContext() {
        Parrot parrot = context.getBean(Parrot.class);
        assertEquals("Koko", parrot.getName());
    }

}
