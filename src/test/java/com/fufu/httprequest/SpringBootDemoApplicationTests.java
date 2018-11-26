package com.fufu.httprequest;

import com.fufu.SpringBootDemoApplication;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootDemoApplication.class)
public class SpringBootDemoApplicationTests {
//    @Test
//    public void contextLoads() {
//        try {
//            String result = HttpUtil.httpRequest("http://localhost:8080/robotservice/visitorRecord/qryVisitedUnit","POST","application/x-www-form-urlencoded","telNum=1555");
//            String jobj = JSON.toJSONString(result);;
//            System.out.println(jobj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void passwordMD5() {
        String hashAlgorithName = "MD5";
        String password = "123";
        int hashIterations = 1024;//加密次数
        ByteSource credentialsSalt = ByteSource.Util.bytes("guest");
        Object obj = new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
        System.out.println(obj);
    }
}
