package com.fufu.controller;

import com.fufu.entity.BlogVisitor;
import com.fufu.entity.UserInfo;
import com.fufu.service.RestfulService;
import com.fufu.tools.JsonUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@EnableTransactionManagement
@RequestMapping(value = "/blogvisitor")
public class RestfulController {
    @Autowired
    RestfulService restfulService;
    @Value("${test.aconfig}")
    private String aconfig;

    @ApiOperation(value="添加访客", notes="添加访客")
    @PostMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public String addBlogVisitor(@RequestBody BlogVisitor blogVisitor) {
        try {
            restfulService.addBlogVisitor(blogVisitor);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "保存访问者失败!").pushData();
        }
        return JsonUtil.getInstance().putData("ret", 1).putData("data",blogVisitor).putData("msg", "保存访问者成功!").pushData();
    }

    @ApiOperation(value="删除访客", notes="删除访客")
    @ApiImplicitParam(name = "id", value = "访客id", required = true, dataType = "Long")
    @GetMapping(value = "/delete", produces = {"application/json;charset=UTF-8"})
    public String deleteBlogVisitor(Long id) {
        try {
            restfulService.deleteBlogVisitor(id);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "删除访问者失败!").pushData();
        }
        return JsonUtil.getInstance().putData("ret", 1).putData("msg", "删除访问者成功!").pushData();
    }

    @ApiOperation(value="更新访客", notes="更新访客")
    @PostMapping(value = "/update", produces = {"application/json;charset=UTF-8"})
    public String updateBlogVisitor(@RequestBody BlogVisitor blogVisitor) {
        try {
            restfulService.updateBlogVisitor(blogVisitor);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "更新访问者失败!").pushData();
        }
        return JsonUtil.getInstance().putData("ret", 1).putData("msg", "更新访问者成功!").pushData();
    }

    @ApiOperation(value="查询访客列表", notes="查询访客列表")
    @GetMapping(value = "/qry")
    public String qryBlogVisitorList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize); //pageNum表示页数, pageSize表示每页的大小
        PageHelper.orderBy("visit_time DESC"); //进行分页结果的排序，visit_time为字段名，排序规则DESC/ASC
        List<BlogVisitor> resultList = null;
        PageInfo pageInfo = null;
        try {
            resultList = restfulService.qryBlogVisitorList();
            pageInfo = new PageInfo<BlogVisitor>(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "查询访问者失败!").pushData();
        }
        if(resultList == null)
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "查询不到数据!").pushData();
        return JsonUtil.getInstance().putData("ret", 1).putData("data",pageInfo).putData("msg", "查询访问者成功!").pushData();
    }

    @ApiOperation(value="查询用戶列表", notes="查询用戶列表")
    @GetMapping(value = "/qryUser")
    public String qryUserList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize); //pageNum表示页数, pageSize表示每页的大小
        List<UserInfo> resultList = null;
        PageInfo pageInfo = null;
        try {
            resultList = restfulService.qryUserList();
            pageInfo = new PageInfo<UserInfo>(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "查询用戶失败!").pushData();
        }
        if(resultList == null)
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "查询不到数据!").pushData();
        return JsonUtil.getInstance().putData("ret", 1).putData("data",pageInfo).putData("msg", "查询用戶成功!").pushData();
    }

    @ApiOperation(value="添加用户", notes="添加用户")
    @PostMapping(value = "/addUser", produces = {"application/json;charset=UTF-8"})
    public String addUser(@RequestBody UserInfo userInfo) {
            restfulService.addUserInfo(userInfo);
//            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "保存访问者失败!").pushData();
        return JsonUtil.getInstance().putData("ret", 1).putData("data",userInfo).putData("msg", "保存访问者成功!").pushData();
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void autoTask() {
        System.out.println("-----我是一个五秒的定时任务-----");
    }

    @PostMapping(value = "/excelUpload")
    @ResponseBody
    public String uploadExcel(@RequestParam("file") MultipartFile file) throws Exception {
        if(file==null || file.isEmpty()){
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "上传文件不能为空！").pushData();
        }
        try{
            return restfulService.uploadSatelliteExcel(file);
        }catch(Exception e){
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", e.getMessage()).pushData();
        }
    }


}
