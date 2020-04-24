package com.zby.service.impl;

import com.zby.mapper.UserDao;
import com.zby.pojo.User;
import com.zby.service.UserService;
import com.zby.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User checkUser(String username, String password) throws Exception {
        return userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
