package com.example.demo.services;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    UserInfoMapper mapper;

    @Override
    public List<UserInfo> findAll(){
        List<UserInfo> userInfos = mapper.findAll();
        return  userInfos;
    }

    @Override
    public UserInfo findById(Integer eid) {
        UserInfo userInfo = mapper.findById(eid);
        if (userInfo!=null)
            return userInfo;
        return null;
    }

    @Override
    public void addUser(UserInfo userInfo) {
        mapper.addUser(userInfo);
    }

    @Override
    public void modifyUser(UserInfo userInfo) {
//        int eid = userInfo.getEid();
        mapper.modifyUser(userInfo);
    }

    @Override
    public void deleteUser(Integer eid) {
        mapper.deleteUser(eid);

    }

}
