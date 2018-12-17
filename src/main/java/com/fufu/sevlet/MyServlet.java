package com.fufu.sevlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "myServlet", urlPatterns = "/myServlet")
public class MyServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(MyServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("发起servlet请求:{}",req.getPathInfo());
        PrintWriter writer = resp.getWriter();
        writer.write("hello world!");
        writer.flush();
    }
}
