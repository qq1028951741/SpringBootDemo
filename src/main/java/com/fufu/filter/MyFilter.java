package com.fufu.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MyFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //对request和response进行一些预处理
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //请求的地址
        String url = httpRequest.getRequestURI();
        logger.info("请求的地址: {}", url);
        //这里可以加过滤条件
        //response.sendRedirect(request.getContextPath()+"/fail.jsp"); 过滤失败可以重定向
        //进入下一个过滤器
        chain.doFilter(httpRequest, httpResponse);
    }

    @Override
    public void destroy() {

    }
}
