package com.zby.mapper;

import com.zby.pojo.Blog;
import com.zby.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TypeDao {


    @Insert("insert into t_type(name) values(#{name})")
    Integer saveType(Type type) throws Exception;


    @Delete("delete from t_type where id=#{id}")
    void deleteType(Long id) throws Exception;


    @Update("update t_type set name=#{name} where id=#{id}")
    Integer updateType(@Param("name")String name,@Param("id")Long id) throws Exception;

    @Select("select * from t_type where id=#{id}")
    Type getType(Long id) throws Exception;

    @Select("select count(id) from t_type where name=#{name}")
    Integer getTypeByName(String name) throws Exception;

    @Select("select * from t_type")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "blogs", column = "id", javaType = List.class, many = @Many(select = "com.zby.mapper.BlogDao.getBlogJoinType"))
    })
    List<Type> findAll() throws Exception;

    @Select("select * from t_type")
    List<Type> getAdminType();

    @Select("select * from t_type where id=#{id}")
    Type getTypeById(Long Id);

    @Select("select * from t_type limit 6")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "blogs", column = "id", javaType = List.class, many = @Many(select = "com.zby.mapper.BlogDao.getBlogJoinType"))
    })
    List<Type> indexType();


}
