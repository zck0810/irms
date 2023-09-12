package com.fangzai.controller;

import com.fangzai.entity.Result;
import com.fangzai.entity.User;
import com.fangzai.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequestMapping("/UserController")
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getUserList")
    public Result list(){
        List<User> userList =  userService.list();
        return Result.success(userList);
    }

    @PostMapping("insertUser")
    public Result add(){
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setLoginId("www");
        user.setPassword("789");
        user.setUserName("王五");
        user.setUserTelephone("15698556485");
        user.setDepartment("gis");
        user.setUnit("防灾");
        user.setAccountStatus("激活");
        user.setUserPermission("超级管理员");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.add(user);
        return Result.success();
    }

}
