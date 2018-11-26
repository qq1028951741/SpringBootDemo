package com.fufu.mapper;

import com.fufu.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAuthMapper{
    User findByUsername(String username);
}