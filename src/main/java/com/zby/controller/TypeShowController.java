package com.zby.controller;

import com.github.pagehelper.PageInfo;
import com.zby.pojo.Blog;
import com.zby.pojo.Type;
import com.zby.service.BlogService;
import com.zby.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;

    @GetMapping("/types/{typeId}")
    public String types(@RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                        @RequestParam(name = "size",required = true,defaultValue = "6") Integer size,@PathVariable Long typeId,Model model) throws Exception {

        List<Type> types =typeService.findAllNoPage();

        List<Blog> blogs = null;
        if (typeId==-1){
            blogs = blogService.indexBlog(page, size);
        }else {
            blogs = blogService.getByTypeId(typeId, page, size);
        }

        PageInfo pageInfo = new PageInfo(blogs);
        model.addAttribute("types",types);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",typeId);

        return "types";

    }


}
