package com.zby.controller.admin;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zby.pojo.Blog;
import com.zby.pojo.Tag;
import com.zby.pojo.User;
import com.zby.service.BlogService;
import com.zby.service.TagService;
import com.zby.service.TypeService;
import com.zby.vo.SearchBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    TypeService typeService;
    @Autowired
    TagService tagService;

    public void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.getAdminType());
        model.addAttribute("tags", tagService.getAdminTag());
    }
    @GetMapping("/blogs")
    public String blogs(Model model, @RequestParam(name = "page",required = true,defaultValue = "1") Integer page, @RequestParam(name = "size",required = true,defaultValue = "4") Integer size) throws Exception {
        List<Blog> blogs = blogService.queryBlog(page,size);
        PageInfo pageInfo = new PageInfo(blogs);
        model.addAttribute("pageInfo",pageInfo);

        setTypeAndTag(model);
        return "admin/blog/blogs";
    }

    @PostMapping("/blogs/search")
    public String search(SearchBlog searchBlog, Model model,
                         @RequestParam(name = "page",required = true,defaultValue = "1") Integer page,
                         @RequestParam(name = "size",required = true,defaultValue = "4") Integer size) {
        //将recommend转换一下
        blogService.transformRecommend(searchBlog);
        //动态sql可以解决
        List<Blog> blogBySearch = blogService.getBlogBySearch(searchBlog);
        PageHelper.startPage(page, size);
        PageInfo pageInfo = new PageInfo(blogBySearch);
        model.addAttribute("pageInfo", pageInfo);
        setTypeAndTag(model);
        model.addAttribute("message", "查询成功");
        return "admin/blog/blogs";
    }

    //去新增页面
    @GetMapping("/blogs/input")
    public String toAdd(Model model) {

        setTypeAndTag(model);
        return "admin/blog/blogs-input";
    }


    @PostMapping("/blogs/saveBlog")
    public String saveBlog(Blog blog,HttpSession session,RedirectAttributes attributes) throws Exception {
        blog.setUser((User) session.getAttribute("user"));
        blog.setUserId(blog.getUser().getId());
//        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTypeId(blog.getType().getId());
        //blog和tag是多对多查询 只需要把tagid和blogid新增至blog_tag表里
        blog.setTags(tagService.getTagByString(blog.getTagIds()));
        Integer i = blogService.saveBlog(blog);
        blogService.saveBlogAndTag(blog);
        if (i != 1) {
            attributes.addFlashAttribute("message", "发布失败");
        } else {
            attributes.addFlashAttribute("message", "发布成功");
        }
        return "redirect:/admin/blogs";

    }

    //去修改页面
    @GetMapping("/blogs/{id}/edit")
    public String toEdit(@PathVariable Long id,Model model) {
//        model.addAttribute("types", typeService.getAdminType());
//        model.addAttribute("tags", tagService.getAdminTag());
        setTypeAndTag(model);

        Blog blog = blogService.getBlogById(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blog/blogs-edit";
    }

    @PostMapping("/blogs/edit")
    public String edit(Blog blog){
        blog.setTypeId(blog.getType().getId());
        blog.setTags(tagService.getTagByString(blog.getTagIds()));
        blogService.updateBlogAndTag(blog);
        blogService.updateBlog(blog);

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }








}
