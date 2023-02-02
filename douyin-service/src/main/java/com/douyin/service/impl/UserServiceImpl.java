package com.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.common.vo.UserResponseVo;
import com.douyin.entity.User;
import com.douyin.mapper.UserMapper;
import com.douyin.service.UserService;
import com.douyin.utils.JwtUtils;
import com.douyin.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

/**
 * Author:WJ
 * Date:2023/2/1 13:00
 * Description:<>
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Transactional
    public UserResponseVo register(String username, String password) {
        // 正则表达式验证邮箱合法性
        String pattern = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        if (!Pattern.matches(pattern, username)) {
            return UserResponseVo.fail("请输入正确的邮箱");
        }
        // 验证该邮箱是否已经注册过了
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = this.getOne(queryWrapper);

        if (user == null) {
            String pwdMD5 = MD5Utils.parseStrToMd5L32(password);
            user = new User();
            user.setUsername(username);
            user.setPassword(pwdMD5);
            this.save(user);
            String token = JwtUtils.createToken(user.getId(), username);
            return UserResponseVo.success(user.getId(), token);
        } else {
            return UserResponseVo.fail("注册失败，该邮箱已被注册");
        }
    }

    @Override
    public UserResponseVo login(String username, String password) {
        // 1.查询是否存在该用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            return UserResponseVo.fail("登录失败，该用户不存在");
        }
        // 2.验证密码是否正确
        String pwMD5 = MD5Utils.parseStrToMd5L32(password);
        if (user.getPassword().equals(pwMD5)) {
            String token = JwtUtils.createToken(Long.valueOf(user.getId()), username);
            return UserResponseVo.success(user.getId(), token);
        } else {
            return UserResponseVo.fail("登录失败，密码不正确");
        }
    }
}
