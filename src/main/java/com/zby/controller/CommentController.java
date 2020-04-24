package com.zby.controller;

import com.zby.pojo.Comment;
import com.zby.pojo.User;
import com.zby.service.BlogService;
import com.zby.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

@Controller
public class CommentController {

    @Value("${common.avatar}")
    private String avatar;
    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        List<Comment> comments = commentService.getCommentById(blogId);

        model.addAttribute("comments",comments);

        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String saveComment(Comment comment, HttpSession session){
        Long blogId = comment.getBlog().getId();
//        comment.setBlog(blogService.getBlogById(blogId));
        comment.setBlogId(blogId);
        User user = (User)session.getAttribute("user");
        if (user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }

        commentService.saveComment(comment);
        return "redirect:/comments/"+blogId;
    }
}
