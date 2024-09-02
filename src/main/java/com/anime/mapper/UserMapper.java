package com.anime.mapper;

import com.anime.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User>{
//    @Select("select * from user")
//    List<User> findAll();
}
