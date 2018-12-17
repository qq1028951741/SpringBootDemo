package com.fufu.config;

import com.fufu.filter.MyFilter;
import com.fufu.listener.MyListener;
import com.fufu.sevlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean myFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        MyFilter filter = new MyFilter();
        registrationBean.setFilter(filter);

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);

        return registrationBean;
    }

    @Bean
    public ServletRegistrationBean myServlet() {
        MyServlet demoServlet = new MyServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(demoServlet);
        List<String> urlMappings = new ArrayList<String>();
        urlMappings.add("/myServlet");////访问，可以添加多个
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean myListener() {
        ServletListenerRegistrationBean registrationBean
                = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new MyListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
