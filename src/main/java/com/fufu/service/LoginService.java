package com.fufu.service;

import com.fufu.annotation.DS;
import com.fufu.entity.User;
import com.fufu.mapper.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginService {
    @Autowired private UserAuthMapper userAuthMapper;

    //shiro鉴权
    @DS("datasource1")
    public User findByUsername(String username) {
        return userAuthMapper.findByUsername(username);
    }
}
