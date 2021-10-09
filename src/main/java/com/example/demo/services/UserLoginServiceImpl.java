package com.example.demo.services;

import com.example.demo.mapper.UserLoginMapper;
import com.example.demo.pojo.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLoginServiceImpl implements UserLoginService {
    @Autowired
    UserLoginMapper mapper;
    @Override
    public List<UserLogin> queryAll() {
        List<UserLogin> logins = mapper.queryAll();

        return logins;
    }

    @Override
    public void add(UserLogin userLogin) {
        mapper.add(userLogin);
    }

    @Override
    public UserLogin queryByName(String username) {
        UserLogin ul = mapper.queryByName(username);
        if (ul!=null)
            return ul;
        return null;
    }

    @Override
    public UserLogin login(String username, String password) {
        UserLogin ul = mapper.login(username,password);
        return ul;
    }
}
