package com.example.config;

import com.example.proxies.CommentNotificationProxy;
import com.example.proxies.EmailCommentNotificationProxy;
import com.example.repositories.CommentRepository;
import com.example.repositories.DBCommentRepository;
import com.example.services.CommentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfiguration {

    @Bean
    CommentNotificationProxy commentNotificationProxy() {
        return new EmailCommentNotificationProxy();
    }

    @Bean
    CommentRepository commentRepository() {
        return new DBCommentRepository();
    }

    @Bean
    CommentService commentService(CommentRepository commentRepository, CommentNotificationProxy commentNotificationProxy) {
        return new CommentService(commentRepository, commentNotificationProxy);
    }
}
