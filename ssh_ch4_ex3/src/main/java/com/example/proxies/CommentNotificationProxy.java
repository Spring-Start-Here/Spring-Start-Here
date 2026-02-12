package com.example.proxies;

import com.example.model.Comment;

public interface CommentNotificationProxy {

    void sendComment(Comment comment);
}
