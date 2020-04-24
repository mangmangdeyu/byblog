package com.zby.service;

import com.zby.pojo.Comment;

import java.util.List;

public interface CommentService {

    Integer saveComment(Comment comment);

    List<Comment> getCommentById(Long blogId);
}
