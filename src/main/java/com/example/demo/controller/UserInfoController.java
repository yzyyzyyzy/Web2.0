package com.example.demo.controller;


import com.example.demo.pojo.UserInfo;
import com.example.demo.services.UserInfoServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserInfoController {
    @Autowired
    UserInfoServiceImpl userInfoService;

    @GetMapping(value = "/findAll")
    public String findAll(Model model){
        model.addAttribute("users", userInfoService.findAll());
        return "index";
    }


    @RequestMapping(value = "/findByid" ,method = RequestMethod.POST)
    public String findByid(@Param("eid") Integer eid,Model model){
//        UserInfo userInfo = userInfoService.findById(id);
        model.addAttribute("users", userInfoService.findById(eid));

        return "index";
    }


    @GetMapping(value = "/toAdd")
    public String toSave() {

        return "addUser";
    }


    @PostMapping(value = "/addUser")
    public String addUser(UserInfo userInfo){
        String name = TransactSQLInjection(userInfo.getName());
        String age = TransactSQLInjection(userInfo.getAge());
        String sex =TransactSQLInjection(userInfo.getSex());
        userInfo.setName(name);
        userInfo.setAge(age);
        userInfo.setSex(sex);
        userInfoService.addUser(userInfo);
        return "redirect:/findAll";
    }

    @GetMapping(value = "/findModify")
    public String find(Integer eid, Model model) {
        model.addAttribute("user", userInfoService.findById(eid));
        return "/modifyUser";
    }

    @PostMapping(value = "/modifyUser")
    public String modifyUser(UserInfo userInfo){
        userInfoService.modifyUser(userInfo);
        return "redirect:/findAll";
    }

    @RequestMapping(value = "/deleteUser")
    public String deleteUser(Integer eid){
        userInfoService.deleteUser(eid);
        return "redirect:/findAll";
    }

    public static String TransactSQLInjection(String str)
    {
        return str.replaceAll(".*([%';]+|(--)+).*", " ");
    }

}
