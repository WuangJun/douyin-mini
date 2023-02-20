package com.douyin.manager;

import com.alibaba.fastjson.JSON;
import com.douyin.common.vo.UserResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:WJ
 * Date:2023/2/18 21:49
 * Description:<>
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagerTest {

    @Autowired
    private UserManager userManager;

    @Test
    public void login(){
        String username="158187";
        String password="123456";
        UserResponseVO vo = userManager.login(username, password);
        log.info(JSON.toJSONString(vo));
    }
}
