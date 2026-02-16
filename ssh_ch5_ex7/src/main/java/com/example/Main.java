package com.example;

import com.example.config.ProjectConfig;
import com.example.model.Comment;
import com.example.services.CommentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    static void main() {

        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        CommentService service = context.getBean(CommentService.class);

        service.sendComment(new Comment());
    }
}
