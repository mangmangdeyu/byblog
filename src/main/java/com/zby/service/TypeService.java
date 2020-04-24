package com.zby.service;

import com.zby.pojo.Type;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TypeService {



    Integer saveType(Type type) throws Exception;


    void deleteType(Long id) throws Exception;


    Integer updateType(String name,Long id) throws Exception;


    Type getType(Long id) throws Exception;

    Integer getTypeByName(String name) throws Exception;

    List<Type> findAll(Integer page, Integer size) throws Exception;

    List<Type> getAdminType();

    List<Type> indexType();

    List<Type> findAllNoPage() throws Exception;
}
