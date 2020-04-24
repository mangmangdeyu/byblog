package com.zby.service;

import com.zby.pojo.Tag;
import com.zby.pojo.Type;

import java.util.List;

public interface TagService {



    Integer saveTag(Tag tag) throws Exception;


    void deleteTag(Long id) throws Exception;


    Integer updateTag(String name, Long id) throws Exception;


    Tag getTag(Long id) throws Exception;

    Integer getTagByName(String name) throws Exception;

    List<Tag> findAll(Integer page, Integer size) throws Exception;

    List<Tag> getAdminTag();

    List<Tag> getTagByString(String ids);

    List<Tag> indexTag();

    List<Tag> findAllNoPage() throws Exception;
}
