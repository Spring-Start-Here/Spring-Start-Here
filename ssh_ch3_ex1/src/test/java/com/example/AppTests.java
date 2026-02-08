package com.example;

import com.example.beans.Parrot;
import com.example.beans.Person;
import com.example.config.ProjectConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    @DisplayName("Test that the person 'Ella' is in the Spring context")
    void testEllaIsInContext() {
        Person person = context.getBean(Person.class);
        assertEquals("Ella", person.getName());
    }

    @Test
    @DisplayName("Test that the person 'Ella' and parrot 'Koko' are not linked")
    void testEllaAndKokoAreNotLinked() {
        Person person = context.getBean(Person.class);
        assertNull(person.getParrot());
    }
}
