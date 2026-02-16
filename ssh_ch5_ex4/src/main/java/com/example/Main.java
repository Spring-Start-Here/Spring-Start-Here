package com.example;

import com.example.config.ProjectConfig;
import com.example.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        var c = new AnnotationConfigApplicationContext(ProjectConfig.class);

        System.out.println("Before retrieving the CommentService");
        var commentService = c.getBean("commentService", CommentService.class);
        System.out.println("After retrieving the CommentService");
    }

}
