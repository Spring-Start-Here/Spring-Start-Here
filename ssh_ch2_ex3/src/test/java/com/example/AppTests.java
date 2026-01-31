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
        Parrot parrot = context.getBean("parrot1", Parrot.class);
        assertEquals("Koko", parrot.getName());
    }

    @Test
    @DisplayName("Test that the parrot 'Miki' is in the Spring context")
    void testMikiIsInContext() {
        Parrot parrot = context.getBean("parrot2", Parrot.class);
        assertEquals("Miki", parrot.getName());
    }

    @Test
    @DisplayName("Test that the parrot 'Riki' is in the Spring context")
    void testRikiIsInContext() {
        Parrot parrot = context.getBean("parrot3", Parrot.class);
        assertEquals("Riki", parrot.getName());
    }

}
