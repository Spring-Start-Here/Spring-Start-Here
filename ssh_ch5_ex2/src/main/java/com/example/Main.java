package com.example;

import com.example.config.ProjectConfig;
import com.example.services.CommentService;
import com.example.services.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {
        var c = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var commentService = c.getBean("commentService", CommentService.class);
        var userService = c.getBean("userService", UserService.class);

        boolean b1 = commentService.getCommentRepository() == userService.getCommentRepository();

        System.out.println(b1);
    }

}
