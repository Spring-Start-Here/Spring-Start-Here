package com.example;

import com.example.config.ProjectConfig;
import com.example.model.Comment;
import com.example.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        CommentService s1 = context.getBean(CommentService.class);
        CommentService s2 = context.getBean(CommentService.class);

        boolean b = s1.getCommentProcessor() == s2.getCommentProcessor();

        System.out.println(b);
    }
}
