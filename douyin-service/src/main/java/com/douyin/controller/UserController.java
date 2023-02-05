package com.douyin.controller;

import com.douyin.aspect.ClientLogin;
import com.douyin.common.dto.LoginUserDTO;
import com.douyin.common.dto.UserDTO;
import com.douyin.common.vo.UserInfoResponseVO;
import com.douyin.common.vo.UserResponseVO;
import com.douyin.manager.UserManage;
import com.douyin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:WJ
 * Date:2023/2/1 13:29
 * Description:<>
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManage userManage;

    /**
     *
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public UserResponseVO register(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userManage.register(username, password);
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    public UserResponseVO login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userManage.login(username, password);
    }

    /**
     * 用户信息查询
     * @param userId
     * @param token
     * @return
     */
    @ClientLogin
    @GetMapping("/")
    public UserInfoResponseVO getUserInfo(@RequestParam("user_id") Long userId, @RequestParam("token")String token, LoginUserDTO loginUserDTO){
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(loginUserDTO,userDto);
        return UserInfoResponseVO.success(userDto);
    }
}
