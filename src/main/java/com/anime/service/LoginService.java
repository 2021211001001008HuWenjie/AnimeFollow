package com.anime.service;

import com.anime.domain.ResponseResult;
import com.anime.entity.User;

public interface LoginService {
    ResponseResult login(User user);
}
