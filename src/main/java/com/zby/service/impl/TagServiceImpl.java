package com.zby.service.impl;

import com.github.pagehelper.PageHelper;
import com.zby.mapper.TagDao;
import com.zby.mapper.TypeDao;
import com.zby.pojo.Tag;
import com.zby.pojo.Type;
import com.zby.service.TagService;
import com.zby.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public Integer saveTag(Tag tag) throws Exception {
        return tagDao.saveTag(tag);
    }

    @Override
    public void deleteTag(Long id) throws Exception {
        tagDao.deleteTag(id);
    }

    @Override
    public Integer updateTag(String name, Long id) throws Exception {
        Integer count = tagDao.getTagByName(name);
        if (count!=0){
            return 0;
        }
        return tagDao.updateTag(name,id);
    }

    @Override
    public Tag getTag(Long id) throws Exception {
        return tagDao.getTag(id);
    }

    @Override
    public Integer getTagByName(String name) throws Exception {
        return tagDao.getTagByName(name);
    }

    @Override
    public List<Tag> findAll(Integer page, Integer size) throws Exception {
        //参数pageNum 是页码值   参数pageSize 代表是每页显示条数
        PageHelper.startPage(page, size);
        return tagDao.findAll();
    }

    @Override
    public List<Tag> getAdminTag() {
        return tagDao.getAdminTag();
    }

    @Override
    public List<Tag> getTagByString(String ids) {
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = convertToList(ids);
        for (Long l : longs){
            tags.add(tagDao.getById(l));
        }
        return tags;
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> indexTag() {
        return tagDao.indexTag();
    }

    @Override
    public List<Tag> findAllNoPage() throws Exception{
        return tagDao.findAll();
    }
}
