package com.example.services;

import com.example.config.ProjectConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotSame;

public class CommentServiceTest {

    @Test
    @DisplayName("""
            GIVEN the Spring context contains a prototype bean definition for CommentService
            WHEN the CommentService bean is requested twice from the context
            THEN the context should return two different instances
            """)
    void testCommentServiceIsPrototype() {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var cs1 = context.getBean(CommentService.class);
        var cs2 = context.getBean(CommentService.class);

        assertNotSame(cs1, cs2);
    }
}
