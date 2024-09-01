package com.anime.controller;

import com.anime.entity.User;
import com.anime.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping ("/user")
    public List<User> index(){
        System.out.println("123");
        return userMapper.findAll();
    }

    @PostMapping("/test")
    public String test(String username,String password){
        System.out.println("666");
        System.out.println(username + password);
        return "ok";
    }
}
