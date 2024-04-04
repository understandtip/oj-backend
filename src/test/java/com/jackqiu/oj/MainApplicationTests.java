package com.jackqiu.oj;

import com.jackqiu.oj.config.WxOpenConfig;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 *  
 */
@SpringBootTest
class MainApplicationTests {

    @Value("${spring.datasource.url}")
    public String profile;

    @Resource
    private WxOpenConfig wxOpenConfig;

    @Test
    void contextLoads() {
        System.out.println(wxOpenConfig);
        System.out.println(profile);
    }

}
