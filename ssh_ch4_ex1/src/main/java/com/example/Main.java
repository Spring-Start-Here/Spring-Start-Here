package com.example;

import com.example.model.Comment;
import com.example.proxies.EmailCommentNotificationProxy;
import com.example.repositories.DBCommentRepository;
import com.example.services.CommentService;

public class Main {

    static void main() {
        var commentRepository = new DBCommentRepository();
        var commentNotificationProxy = new EmailCommentNotificationProxy();

        var commentService = new CommentService(commentRepository, commentNotificationProxy);

        var comment = new Comment();
        comment.setAuthor("Laurentiu");
        comment.setText("Demo comment");

        commentService.publishComment(comment);
    }
}
