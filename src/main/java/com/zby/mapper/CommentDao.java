package com.zby.mapper;

import com.zby.pojo.Blog;
import com.zby.pojo.Comment;
import com.zby.pojo.Type;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {


    @Select("select * from t_comment where blogId=#{blogId} order by createTime asc")
    @Results({
            @Result(property = "parentComment", column = "parentCommentId", javaType = Comment.class, one = @One(select = "com.zby.mapper.CommentDao.getParent"))

    })
    List<Comment> getCommentById(Long blogId);

//
    @Select("select id,nickName from t_comment where id=#{id}")
    Comment getParent(Long id);

    @Insert("insert into t_comment(avatar,content,createTime,email,nickName,blogId,parentCommentId,adminComment) " +
            "values(#{avatar},#{content},#{createTime},#{email},#{nickName},#{blogId},#{parentCommentId},#{adminComment})")
    Integer saveComment(Comment comment);

    @Select("select * from t_comment where blogId=#{blogId} and parentCommentId = -1 order by createTime desc")
    List<Comment> getCommentByIdNoFather(@Param("blogId") Long blogId);

    @Select("select * from t_comment where parentCommentId = #{id}")
    @Results({
            @Result(property = "parentComment", column = "parentCommentId", javaType = Comment.class, one = @One(select = "com.zby.mapper.CommentDao.getParent"))

    })
    List<Comment> getReply(Long id);
}
