package com.zby.mapper;

import com.zby.pojo.Tag;
import com.zby.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagDao {


    @Insert("insert into t_tag(name) values(#{name})")
    Integer saveTag(Tag tag) throws Exception;


    @Delete("delete from t_tag where id=#{id}")
    void deleteTag(Long id) throws Exception;


    @Update("update t_tag set name=#{name} where id=#{id}")
    Integer updateTag(@Param("name") String name, @Param("id") Long id) throws Exception;

    @Select("select * from t_tag where id=#{id}")
    Tag getTag(Long id) throws Exception;

    @Select("select count(id) from t_tag where name=#{name}")
    Integer getTagByName(String name) throws Exception;

    @Select("select * from t_tag")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "blogs", column = "id", javaType = List.class, many = @Many(select = "com.zby.mapper.BlogDao.getBlogJoinTag"))
    })
    List<Tag> findAll() throws Exception;

    @Select("select * from t_tag")
    List<Tag> getAdminTag();

    @Select("select * from t_tag where id=#{id}")
    Tag getById(Long id);

    @Select("select * from t_tag where id in (select tagsId from t_blog_tags where blogsId = #{id})")
    List<Tag> findTagByBlogId(Long id);


    @Select("select * from t_tag limit 6")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "blogs", column = "id", javaType = List.class, many = @Many(select = "com.zby.mapper.BlogDao.getBlogJoinTag"))
    })
    List<Tag> indexTag();



}
