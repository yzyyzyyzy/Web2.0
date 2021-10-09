package com.example.demo.mapper;

import com.example.demo.pojo.UserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserLoginMapper {
    //查询
    public List<UserLogin> queryAll();
    //添加数据
    public void add(UserLogin userLogin);
    //登录
    public UserLogin login(String username, String password);

    //根据用户名查询数据
    public UserLogin queryByName(String username);
}
