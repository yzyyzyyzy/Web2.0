package com.example.demo.services;

import com.example.demo.pojo.UserLogin;

import java.util.List;

public interface UserLoginService {
    //查询
    public List<UserLogin> queryAll();

    //添加数据
    public void add(UserLogin userLogin);
    //根据用户名查询数据
    public UserLogin queryByName(String username);

//    登录
    public UserLogin login(String username,String password);
}
