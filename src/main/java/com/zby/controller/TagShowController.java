package com.zby.controller;

import com.github.pagehelper.PageInfo;
import com.zby.pojo.Blog;
import com.zby.pojo.Tag;

import com.zby.service.BlogService;
import com.zby.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    BlogService blogService;
    @Autowired
    TagService tagService;

    @GetMapping("/tags/{tagId}")
    public String tags(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                        @RequestParam(name = "size",required = true,defaultValue = "6") Integer size,@PathVariable Long tagId,Model model) throws Exception {

        List<Tag> tags =tagService.findAllNoPage();

        List<Blog> blogs = null;
        if (tagId==-1){
            blogs = blogService.indexBlog(page, size);
        }else {
            blogs = blogService.getByTagId(tagId, page, size);
        }

        PageInfo pageInfo = new PageInfo(blogs);
        model.addAttribute("tags",tags);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTagId",tagId);

        return "tags";

    }


}
