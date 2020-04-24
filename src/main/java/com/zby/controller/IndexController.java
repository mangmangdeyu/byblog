package com.zby.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zby.pojo.Blog;
import com.zby.pojo.Tag;
import com.zby.pojo.Type;
import com.zby.service.BlogService;
import com.zby.service.TagService;
import com.zby.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    @GetMapping("/")
    public String index(Model model,@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                        @RequestParam(name = "size",required = true,defaultValue = "6") Integer size){
         List<Blog> indexBlogs =blogService.indexBlog(page,size);
        PageInfo pageInfo = new PageInfo(indexBlogs);

        List<Type> types = typeService.indexType();
        List<Tag> tags = tagService.indexTag();
        List<Blog> recommendBlogs = blogService.getRecommendBlog();
        model.addAttribute("types",types);
        model.addAttribute("tags",tags);
        model.addAttribute("recommendBlogs",recommendBlogs);
        model.addAttribute("pageInfo", pageInfo);
        System.out.println("------------------------");

        for (Type type:types) {
            System.out.println(type.getBlogs().size());
        }
        return "index";

    }
    @PostMapping("/search")
    public String search(Model model,@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                         @RequestParam(name = "size",required = true,defaultValue = "6") Integer size,
                         String query){
        List<Blog> search = blogService.search(query,page,size);
        PageInfo pageInfo = new PageInfo(search);
        model.addAttribute("pageInfo",pageInfo);

        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        Blog blog = blogService.getAndConvert(id);
        model.addAttribute("blog",blog);
        return "blog";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

    @GetMapping("/footer/latestBlog")
    public String latestBlogs(Model model){
        List<Blog>  latestBlogs = blogService.getLatestBlogs();

        model.addAttribute("latestBlog",latestBlogs);
        return "common/_fragments :: latestBlog";
    }
}
