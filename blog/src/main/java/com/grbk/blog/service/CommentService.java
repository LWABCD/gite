package com.grbk.blog.service;

import com.grbk.blog.pojo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论
 */


public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
