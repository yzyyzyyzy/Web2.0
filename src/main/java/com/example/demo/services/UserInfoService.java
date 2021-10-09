package com.example.demo.services;


import com.example.demo.pojo.UserInfo;

import java.util.List;

public interface UserInfoService {
    //查询所有
    public List<UserInfo> findAll();

    //根据id查询
    public UserInfo findById(Integer eid);

    //增加
    public void addUser(UserInfo userInfo);

    //修改
    public void modifyUser(UserInfo userInfo);

    //删除
    public void deleteUser(Integer eid);

}
