package com.zby.service;

import com.zby.pojo.User;

public interface UserService {

     User checkUser(String username, String password) throws Exception;
}
