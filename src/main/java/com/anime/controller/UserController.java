package com.anime.controller;

import com.anime.domain.ResponseResult;
import com.anime.entity.User;
import com.anime.enums.AppHttpCodeEnum;
import com.anime.exception.SystemException;
import com.anime.mapper.UserMapper;
import com.anime.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LoginService loginService;
//    @GetMapping("/user")
//    public List<User> index(){
//        System.out.println("123");
//        return userMapper.findAll();
//    }

    @PostMapping("/test")
    public String test(String username,String password){
        System.out.println("666");
        System.out.println(username + password);
        return "ok";
    }

    @PostMapping("/login")
//ResponseResult是我们在huanf-framework工程里面写的实体类
    public ResponseResult login(@RequestBody User user){
//如果用户在进行登录时，没有传入'用户名'
        if(!StringUtils.hasText(user.getUsername())){
// 提示'必须要传用户名'。AppHttpCodeEnum是我们写的枚举类。SystemException是我们写的统一异常处理的类
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
}
