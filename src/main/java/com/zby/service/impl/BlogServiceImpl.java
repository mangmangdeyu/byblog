package com.zby.service.impl;

import com.github.pagehelper.PageHelper;
import com.zby.exception.NotFoundException;
import com.zby.mapper.BlogDao;
import com.zby.pojo.Blog;
import com.zby.pojo.Tag;
import com.zby.service.BlogService;
import com.zby.service.TagService;
import com.zby.util.MarkdownUtils;
import com.zby.vo.BlogAndTag;
import com.zby.vo.SearchBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogDao blogDao;

    private final String desc="desc";

    @Override
    public Integer saveBlog(Blog blog) {
        blog.setCreatTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        Integer saveBlog = blogDao.saveBlog(blog);

        return saveBlog;
    }

    @Override
    public void saveBlogAndTag(Blog blog) {
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
//        BlogAndTag blogAndTag = null;
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
    //            blogAndTag = new BlogAndTag(blog.getId(),tag.getId());
                blogDao.saveBlogAndTag(blog.getId(),tag.getId());
    //            blogDao.saveBlogAndTag(blogAndTag);
            }
        }
    }

    @Override
    public Integer deleteBlog(Long id) {
        return blogDao.deleteBlog(id);
    }

    @Override
    public Integer updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        return blogDao.updateBlog(blog);
    }

    @Override
    public Blog getBlogById(Long id) {
        Blog blog =blogDao.getBlogById(id);
        return blog;
    }

    @Override
    public List<Blog> queryBlog(Integer page, Integer size) throws Exception {
        PageHelper.startPage(page,size);
        return blogDao.queryBlog();
    }

    @Override
    public void transformRecommend(SearchBlog searchBlog) {
        if (!"".equals(searchBlog.getRecommend()) && null != searchBlog.getRecommend()) {
            searchBlog.setRecommend2(1);
        }
    }

    @Override
    public List<Blog> getBlogBySearch(SearchBlog searchBlog) {

        return blogDao.searchByTitleOrTypeOrRecommend(searchBlog);
    }

    @Override
    public void updateBlogAndTag(Blog blog) {
        List<Tag> tags = blog.getTags();
        blogDao.deleteBlogAndTag(blog.getId());
            for (Tag tag : tags) {
                blogDao.saveBlogAndTag(blog.getId(), tag.getId());
            }
    }

    @Override
    public List<Blog> indexBlog(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return blogDao.indexBlog();
    }

    @Override
    public List<Blog> getRecommendBlog() {
        return blogDao.getRecommendBlog();
    }

    @Override
    public List<Blog> search(String query,Integer page, Integer size) {
        PageHelper.startPage(page,size);
        return blogDao.search(query);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.getBlogById(id);
        if (blog==null){
            throw new NotFoundException("博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogDao.updateViews(id);
        return blog;
    }

    @Override
    public List<Blog> getByTypeId(Long typeId,Integer page, Integer size) {
        return  blogDao.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId, Integer page, Integer size) {
        return blogDao.getByTagId(tagId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogDao.getYears();
        System.out.println(years+"..............................");
        Map<String,List<Blog>> map= new LinkedHashMap<>();
        for (String year : years){
            System.out.println(year+"..............................");
            map.put(year,blogDao.getByYear(year));
        }
        System.out.println(map+"ser......................");
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.countBlog();
    }

    @Override
    public List<Blog> getLatestBlogs() {
        return blogDao.getLatestBlogs();
    }

    @Override
    public Integer deleteTyId(Long id) {
        return null;
    }
}
