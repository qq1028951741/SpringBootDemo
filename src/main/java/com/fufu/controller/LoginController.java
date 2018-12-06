package com.fufu.controller;

import com.alibaba.fastjson.JSONObject;
import com.fufu.annotation.AuthToken;
import com.fufu.constant.SysConstant;
import com.fufu.entity.User;
import com.fufu.service.LoginService;
import com.fufu.tools.JsonUtil;
import com.fufu.tools.Md5TokenGenerator;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import redis.clients.jedis.Jedis;

@Controller
public class LoginController{
    @Autowired LoginService loginService;
    @Autowired Md5TokenGenerator tokenGenerator;
    //用户登录
    @GetMapping("/loginUser")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        //把前端输入的username和password封装为token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            User user=(User) subject.getPrincipal();
            model.addAttribute("username", user.getUsername());
            return "index";
        } catch (Exception e) {
            return "login";
        }
    }

    //退出登录
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "login";
    }

    //访问login时跳到login.html
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    //admin角色才能访问
    @GetMapping("/admin")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "string", paramType = "query")
    })
    @AuthToken
    public String admin() {
        return "admin success";
    }

    //有delete权限才能访问
    @GetMapping("/edit")
    @ResponseBody
    public String edit() {
        return "edit success";
    }

    @GetMapping("/test")
    @ResponseBody
    @RequiresRoles("guest")
    public String test(){
        return "test success";
    }

    @ApiOperation(value="token鉴权", notes="token鉴权")
    @GetMapping(value="loginToken")
    @ResponseBody
    public String login(String username, String password, HttpServletResponse response) {
        User user =  loginService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            Jedis jedis = new Jedis("localhost", 6379);
            String token = tokenGenerator.generate(username, password);
            jedis.set(username, token);
            jedis.expire(username, SysConstant.TOKEN_EXPIRE_TIME);
            jedis.set(token, username);
            jedis.expire(token, SysConstant.TOKEN_EXPIRE_TIME);
            Long currentTime = System.currentTimeMillis();
            jedis.set(token + username, currentTime.toString());
            //用完关闭
            jedis.close();
            return JsonUtil.getInstance().putData("ret", 1).putData("token", token).pushData();
        } else {
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", "token校验失败").pushData();
        }
    }
}