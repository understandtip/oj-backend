package com.jackqiu.oj;

import com.jackqiu.oj.config.WxOpenConfig;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
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
