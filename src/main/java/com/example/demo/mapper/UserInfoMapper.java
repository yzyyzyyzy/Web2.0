package com.example.demo.mapper;


import com.example.demo.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserInfoMapper {
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
