package com.fufu.controller;

import com.fufu.config.websocket.WebSocketServer;
import com.fufu.tools.JsonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("/websocket")
public class MessagePushController {
    //页面请求
    @GetMapping("/requestPage")
    public String websocket() {
        return "websocket";
    }
    //推送数据接口
    @ResponseBody
    @GetMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message,cid);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonUtil.getInstance().putData("ret", -1).putData("msg", cid+"#"+e.getMessage()).pushData();
        }
        return JsonUtil.getInstance().putData("ret", 1).putData("msg", "推送成功cid为："+cid).pushData();
    }

    @Scheduled(cron = "0/5 * * * * ?")
    public void autoPushToWeb() {
        try {
            WebSocketServer.sendInfo("我是一条5秒一次向web推送的消息~","20");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}