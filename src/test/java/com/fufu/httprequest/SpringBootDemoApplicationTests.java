package com.fufu.httprequest;

import com.alibaba.fastjson.JSON;
import com.fufu.tools.HttpUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootDemoApplicationTests {

    @Test
    public void contextLoads() {
        try {
            String result = HttpUtil.httpRequest("http://localhost:8080/robotservice/visitorRecord/qryVisitedUnit","POST","application/x-www-form-urlencoded","telNum=1555");
            String jobj = JSON.toJSONString(result);;
            System.out.println(jobj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
