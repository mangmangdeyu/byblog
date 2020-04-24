package com.zby.mapper;

import com.zby.pojo.Blog;
import com.zby.pojo.Tag;
import com.zby.pojo.Type;
import com.zby.pojo.User;
import com.zby.service.TypeService;
import com.zby.vo.BlogAndTag;
import com.zby.vo.SearchBlog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogDao {

    @Insert(" insert into t_blog(id, title, content, firstPicture, flag, views, appreciation, shareStatement," +
            " commentable, published, recommend, creatTime, updateTime, typeId, userId, description) values(#{id},#{title},#{content},#{firstPicture},#{flag},#{views},#{appreciation},#{shareStatement},#{commentable},#{published},#{recommend},#{creatTime},#{updateTime},#{typeId},#{userId},#{description})")
    @Options(useGeneratedKeys=true,keyProperty="id")
    Integer saveBlog(Blog blog);

    @Delete("delete from t_blog where id=#{id}")
    Integer deleteBlog(Long id);

    @Update(" update t_blog set title=#{title},content=#{content},firstPicture=#{firstPicture},flag=#{flag},appreciation=#{appreciation},shareStatement=#{shareStatement},commentable=#{commentable},published=#{published},recommend=#{recommend},updateTime=#{updateTime},typeId=#{typeId},description=#{description} where id = #{id}")
    Integer updateBlog(Blog blog);

    @Select("select * from t_blog where id=#{id}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "content",column = "content"),
            @Result(property = "firstPicture",column = "firstPicture"),
            @Result(property = "flag",column = "flag"),
            @Result(property = "views",column = "views"),
            @Result(property = "appreciation",column = "appreciation"),
            @Result(property = "shareStatement",column = "shareStatement"),
            @Result(property = "commentable",column = "commentable"),
            @Result(property = "published", column = "published"),
            @Result(property = "recommend", column = "recommend"),
            @Result(property = "creatTime",column = "creatTime"),
            @Result(property = "updateTime",column = "updateTime"),
            @Result(property = "creatTime",column = "creatTime"),
            @Result(property = "description",column = "description"),
            @Result(property = "user",column = "userId", javaType = User.class, one = @One(select = "com.zby.mapper.UserDao.getUserById")),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
            @Result(property = "tags", column = "id",javaType = List.class, many = @Many(select = "com.zby.mapper.TagDao.findTagByBlogId"))
    })
    Blog getBlogById(Long id);

//    @Select("select b.id,b.title,b.updateTime,b.recommend,b.typeId,t.id,t.name from t_blog b left outer join t_type t on b.typeId = t.id order by b.updateTime desc")
    @Select("select id,title,updateTime,recommend,published,typeId from t_blog")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "recommend", column = "recommend"),
            @Result(property = "published",column = "published"),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
    })
    List<Blog> queryBlog();


    @Select({"<script>"+
            "select id,title,updateTime,recommend,typeId from t_blog where 1=1"+
            "<if test='typeId != null'>"+
            "and typeId = #{typeId}"+
            "</if>"+
            "<if test='recommend2 != null'>"+
            "and recommend = #{recommend2}"+
            "</if>"+
            "<if test='title != null'>"+
            "and title LIKE CONCAT('%',#{title},'%')"+
            "</if>"+
            "</script>"
    })
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "recommend", column = "recommend"),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
    })
    List<Blog> searchByTitleOrTypeOrRecommend(SearchBlog searchBlog);


    @Insert("insert into t_blog_tags(blogsId,tagsId) values (#{blogsId},#{tagsId})")
    Integer saveBlogAndTag(@Param("blogsId")Long blogsId, @Param("tagsId")Long tagsId);

    @Delete("delete from t_blog_tags where blogsId = #{blogsId}")
    Integer deleteBlogAndTag(@Param("blogsId")Long blogsId);


    @Select("select id,title,description,userId,updateTime,views,firstPicture,typeId from t_blog")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "views", column = "views"),
            @Result(property = "firstPicture",column = "firstPicture"),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
            @Result(property = "user", column = "userId", javaType = User.class, one = @One(select = "com.zby.mapper.UserDao.getUserById")),
    })
    List<Blog> indexBlog();

    @Select("select id,title from t_blog where typeId=#{typeId}")
    List<Blog> getBlogJoinType(Long typeId);

    @Select("select id, title from t_blog where recommend=1 order by updateTime desc limit 6")
    List<Blog> getRecommendBlog();

    @Select("select id,title from t_blog where id in (select blogsId from t_blog_tags where tagsId = #{tagsId})")
    List<Blog> getBlogJoinTag(Long tagsId);

    @Select("select id,title,description,userId,updateTime,views,firstPicture,typeId from t_blog where  title LIKE CONCAT('%',#{query},'%') or content LIKE CONCAT('%',#{query},'%')")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "views", column = "views"),
            @Result(property = "firstPicture",column = "firstPicture"),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
            @Result(property = "user", column = "userId", javaType = User.class, one = @One(select = "com.zby.mapper.UserDao.getUserById")),
    })
    List<Blog> search(String query);

    @Select("select id,title from t_blog where id=#{id}")
    Blog getBlogJoinComment(Long id);

    @Update("update t_blog set views=views+1 where id=#{id}")
    Integer updateViews(Long id);

    @Select("select id,title,description,userId,updateTime,views,firstPicture,typeId from t_blog where typeId=#{typeId}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "views", column = "views"),
            @Result(property = "firstPicture",column = "firstPicture"),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
            @Result(property = "user", column = "userId", javaType = User.class, one = @One(select = "com.zby.mapper.UserDao.getUserById")),
    })
    List<Blog> getByTypeId(Long typeId);

    @Select("select id,title,description,userId,updateTime,views,firstPicture,typeId from t_blog where id in (select blogsId from t_blog_tags where tagsId = #{tagsId})")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "description", column = "description"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "views", column = "views"),
            @Result(property = "firstPicture",column = "firstPicture"),
            @Result(property = "type", column = "typeId", javaType = Type.class, one = @One(select = "com.zby.mapper.TypeDao.getTypeById")),
            @Result(property = "user", column = "userId", javaType = User.class, one = @One(select = "com.zby.mapper.UserDao.getUserById")),
            @Result(property = "tags", column = "id",javaType = List.class, many = @Many(select = "com.zby.mapper.TagDao.findTagByBlogId"))
    })
    List<Blog> getByTagId(Long tagId);


    @Select("select DATE_FORMAT(updateTime,'%Y') as year FROM t_blog GROUP BY year ORDER BY year desc")
    List<String> getYears();

    @Select("select id,title,updateTime,views,flag from t_blog where date_format(updateTime,'%Y') = #{year}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "title", column = "title"),
            @Result(property = "updateTime", column = "updateTime"),
            @Result(property = "flag",column = "flag"),
           })
    List<Blog>  getByYear(String year);

    @Select("select count(id) from t_blog")
    Long countBlog();

    @Select("select id, title from t_blog order by updateTime desc limit 4")
    List<Blog> getLatestBlogs();

    @Delete("delete typeId from t_blog where typeId=#{id}")
    Integer deleteTypeId(Long typeId);
}
