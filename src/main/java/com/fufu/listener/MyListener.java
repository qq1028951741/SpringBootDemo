package com.fufu.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener("MyListener")
public class MyListener implements HttpSessionListener {
    private static Logger logger = LoggerFactory.getLogger(MyListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("session监听器启动");
        ServletContext context = httpSessionEvent.getSession().getServletContext();
        Integer onLineCount = (Integer) context.getAttribute("onLineCount");
        if(onLineCount==null){
            context.setAttribute("onLineCount", 1);
        }else{
            onLineCount++;
            context.setAttribute("onLineCount", onLineCount);
        }

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("session监听器销毁");
        ServletContext context = httpSessionEvent.getSession().getServletContext();
        Integer onLineCount = (Integer) context.getAttribute("onLineCount");
        if(onLineCount==null){
            context.setAttribute("onLineCount", 1);
        }else{
            onLineCount--;
            context.setAttribute("onLineCount", onLineCount);
        }
    }
}