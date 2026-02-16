package com.example.services;

import com.example.model.Comment;
import com.example.processors.CommentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentProcessor commentProcessor;

    public void sendComment(Comment comment) {
        commentProcessor.setComment(comment);
        commentProcessor.processComment();
    }

    public CommentProcessor getCommentProcessor() {
        return commentProcessor;
    }
}
