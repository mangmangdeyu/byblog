package com.zby.controller;
import com.zby.pojo.Blog;
import com.zby.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {

    @Autowired
    BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> map = blogService.archiveBlog();
        System.out.println(map.keySet());
        Long count = blogService.countBlog();
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",count);

        return "archives";
    }
}
