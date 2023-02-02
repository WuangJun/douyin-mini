package com.douyin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.douyin.common.vo.UserInfoResponseVo;
import com.douyin.common.vo.UserResponseVo;
import com.douyin.entity.User;
import com.douyin.exception.CommonException;
import com.douyin.service.UserService;
import com.douyin.utils.JwtUtils;
import com.douyin.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * Author:WJ
 * Date:2023/2/1 13:29
 * Description:<>
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public UserResponseVo register(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.register(username, password);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public UserResponseVo login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.login(username, password);
    }

    /**
     * 用户信息查询
     * @param userId
     * @param token
     * @return
     */
    @GetMapping("/")
    public UserInfoResponseVo getUserInfo(@RequestParam("user_id") Long userId,@RequestParam("token")String token){
       return null;
    }
}
