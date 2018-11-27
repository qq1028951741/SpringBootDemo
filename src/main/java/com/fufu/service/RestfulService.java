package com.fufu.service;

import com.fufu.config.dynamicdatasource.DS;
import com.fufu.config.excel.ExcelDataFormatter;
import com.fufu.config.excel.ExcelToBean;
import com.fufu.entity.BlogVisitor;
import com.fufu.entity.Satellite;
import com.fufu.entity.User;
import com.fufu.entity.UserInfo;
import com.fufu.mapper.BlogVisitorMapper;
import com.fufu.mapper.SatelliteMapper;
import com.fufu.mapper.UserAuthMapper;
import com.fufu.mapper.UserInfoMapper;
import com.fufu.tools.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Service
@Transactional
public class RestfulService {
    @Autowired private BlogVisitorMapper blogVisitorMapper;
    @Autowired private UserInfoMapper userInfoMapper;
    @Autowired private UserAuthMapper userAuthMapper;
    @Autowired private SatelliteMapper satelliteMapper;

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

    //shiro鉴权
    @DS("datasource1")
    public User findByUsername(String username) {
        return userAuthMapper.findByUsername(username);
    }

    /**
     * 上传卫星excel文件
     */
    public String uploadSatelliteExcel(MultipartFile excelFile){
        ExcelDataFormatter edf = new ExcelDataFormatter();
        InputStream is = null;
        try {
            is = excelFile.getInputStream();

        List<Satellite> list = ExcelToBean.readFromFile(edf,is, Satellite.class);
        satelliteMapper.deleteAll();
        for(Satellite satellite : list){
            satelliteMapper.insert(satellite);
        }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", e.getMessage()).pushData();
        }
        return JsonUtil.getInstance().putData("ret", 1).putData("msg", "上传成功！").pushData();
    }

}
