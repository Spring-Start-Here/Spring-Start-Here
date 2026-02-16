package com.example.services;

import com.example.config.ProjectConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommentServiceTest {

    @Test
    @DisplayName("""
        GIVEN a Spring context with a lazy @Service bean,
        WHEN the context is initialized,
        THEN the bean instance should not be created until it is requested.
        """)
    void testCommentServiceIsLazy() {
        var outContent = new ByteArrayOutputStream();
        var originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        try {
            var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

            String outputAfterContextInitialization = outContent.toString();

            assertFalse(outputAfterContextInitialization.contains("CommentService instance created!"),
                    "CommentService should not be created immediately because it is marked as @Lazy");

            context.getBean(CommentService.class);
            String outputAfterGettingBean = outContent.toString();

            assertTrue(outputAfterGettingBean.contains("CommentService instance created!"),
                    "CommentService should be created when it is first requested");
        } finally {
            System.setOut(originalOut);
        }
    }
}
