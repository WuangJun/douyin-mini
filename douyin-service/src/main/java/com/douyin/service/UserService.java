package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.common.vo.UserResponseVo;
import com.douyin.entity.User;

public interface UserService extends IService<User> {

    public UserResponseVo register(String username, String password);

    public UserResponseVo login(String username, String password);


}
