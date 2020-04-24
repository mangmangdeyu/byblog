package com.zby.service;

import com.zby.mapper.BlogDao;
import com.zby.pojo.Blog;
import com.zby.pojo.Tag;
import com.zby.vo.SearchBlog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Integer saveBlog(Blog blog);

    Integer deleteBlog(Long id);

    Integer updateBlog(Blog blog);

    Blog getBlogById(Long id);

    List<Blog> queryBlog(Integer page, Integer size) throws Exception;

    //修改recommend,因为recommend从前台接收只能接收字符串，但数据库中的Integer类型，所以转一下
    void transformRecommend(SearchBlog searchBlog);

    List<Blog> getBlogBySearch(SearchBlog searchBlog);

    void saveBlogAndTag(Blog blog);

    void updateBlogAndTag(Blog blog);

    List<Blog> indexBlog(Integer page, Integer size);

    List<Blog> getRecommendBlog();

    List<Blog> search(String query,Integer page, Integer size);


    Blog getAndConvert(Long id);

    List<Blog> getByTypeId(Long typeId,Integer page, Integer size);

    List<Blog> getByTagId(Long tagId, Integer page, Integer size);

    Map<String,List<Blog>>archiveBlog();

    Long countBlog();

    List<Blog> getLatestBlogs();

    Integer deleteTyId(Long typeId);
}
