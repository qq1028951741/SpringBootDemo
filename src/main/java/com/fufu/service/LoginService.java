package com.fufu.service;

import com.fufu.config.DS;
import com.fufu.entity.BlogVisitor;
import com.fufu.entity.User;
import com.fufu.entity.UserInfo;
import com.fufu.mapper.BlogVisitorMapper;
import com.fufu.mapper.UserAuthMapper;
import com.fufu.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
