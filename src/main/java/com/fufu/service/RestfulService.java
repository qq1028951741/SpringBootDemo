package com.fufu.service;

import com.fufu.config.DS;
import com.fufu.entity.BlogVisitor;
import com.fufu.entity.UserInfo;
import com.fufu.mapper.BlogVisitorMapper;
import com.fufu.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RestfulService {
    @Autowired private BlogVisitorMapper blogVisitorMapper;
    @Autowired private UserInfoMapper userInfoMapper;

    @DS("datasource1")
    public void addBlogVisitor(BlogVisitor blogVisitor) {
        blogVisitor.setVisitorName("i am tester");
        blogVisitorMapper.insert(blogVisitor);
        throw new ArithmeticException("heiheihei");//事务测试
    }

    @DS("datasource1")
    public void deleteBlogVisitor(Long id) {
        blogVisitorMapper.deleteByPrimaryKey(id);
    }

    @DS("datasource1")
    public void updateBlogVisitor(BlogVisitor blogVisitor) {
        blogVisitorMapper.updateByPrimaryKey(blogVisitor);
    }

    @DS("datasource1")
    public List<BlogVisitor> qryBlogVisitorList() {
        return blogVisitorMapper.selectAll();
    }

    @DS("datasource2")
    public List<UserInfo> qryUserList()throws Exception {
        return userInfoMapper.selectAll();
    }

    @DS("datasource2")
    public void addUserInfo(UserInfo userInfo){
        userInfo.setUserName("i am master");
        userInfoMapper.insert(userInfo);
//        throw new RuntimeException("heiheihei");//事务测试
    }
}
