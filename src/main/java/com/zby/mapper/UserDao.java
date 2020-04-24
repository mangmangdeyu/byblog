package com.zby.mapper;

import com.zby.pojo.Type;
import com.zby.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {


    @Select("select * from t_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password) throws Exception;

    @Select("select * from t_user where id=#{id}")
    User getUserById(Long id) throws Exception;
}
