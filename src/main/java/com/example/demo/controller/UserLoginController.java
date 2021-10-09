package com.example.demo.controller;

import com.example.demo.pojo.UserLogin;
import com.example.demo.services.UserLoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserLoginController {
    @Autowired
    UserLoginServiceImpl userService;
    @RequestMapping("/queryAll")
    @ResponseBody
    public String queryAll(){
        List<UserLogin> list = userService.queryAll();
        return list.toString();
    }
    @ResponseBody
    @RequestMapping("/queryByName")
    public String queryByName(String username){
        UserLogin userLogin = userService.queryByName(username);
        return userLogin.toString();
    }

    @RequestMapping("/register")
    public String regi(){
        return "register";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(UserLogin userLogin){
       userService.add(userLogin);
       return "userlogin";
    }

    @RequestMapping("/webApp/login")
    public String log(){
        return "userlogin";
    }


    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login( UserLogin userLogin){

        UserLogin login = userService.login(userLogin.getUsername(), userLogin.getPassword());
        if (login!=null){
            return "redirect:/findAll";
        }
        return "loginfail";
    }

}
