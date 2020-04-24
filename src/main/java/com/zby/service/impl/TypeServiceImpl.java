package com.zby.service.impl;

import com.github.pagehelper.PageHelper;
import com.zby.mapper.BlogDao;
import com.zby.mapper.TypeDao;
import com.zby.pojo.Blog;
import com.zby.pojo.Type;
import com.zby.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeDao typeDao;
    @Autowired
    BlogDao blogDao;

    @Override
    public Integer saveType(Type type) throws Exception {
        return typeDao.saveType(type);
    }

    @Override
    public void deleteType(Long id) throws Exception {
        blogDao.deleteTypeId(id);
        typeDao.deleteType(id);
    }

    @Override
    public Integer updateType(String name, Long id) throws Exception {
        Integer count = typeDao.getTypeByName(name);
        if (count!=0){
            return 0;
        }
        return typeDao.updateType(name,id);
    }

    @Override
    public Type getType(Long id) throws Exception {
        return typeDao.getType(id);
    }

    @Override
    public Integer getTypeByName(String name) throws Exception {
        return typeDao.getTypeByName(name);
    }

    @Override
    public List<Type> findAll(Integer page, Integer size) throws Exception {
        //参数pageNum 是页码值   参数pageSize 代表是每页显示条数
        PageHelper.startPage(page, size);
        return typeDao.findAll();
    }

    @Override
    public List<Type> indexType() {
        return typeDao.indexType();
    }

    @Override
    public List<Type> getAdminType() {
        return typeDao.getAdminType();
    }

    @Override
    public List<Type> findAllNoPage() throws Exception {
        return typeDao.findAll();
    }
}
