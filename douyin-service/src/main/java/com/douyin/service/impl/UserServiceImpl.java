package com.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.common.dto.UserDTO;
import com.douyin.common.vo.UserVO;
import com.douyin.entity.User;
import com.douyin.entity.Video;
import com.douyin.exception.CommonException;
import com.douyin.mapper.UserMapper;
import com.douyin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Author:WJ
 * Date:2023/2/1 13:00
 * Description:<>
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public Long add(UserDTO userDTO) {
        if (userDTO == null) {
            throw new CommonException("用户信息为空");
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        this.save(user);
        return user.getId();
    }

    @Override
    public UserDTO getByUsername(String username) {
        if (StringUtils.isEmpty(username)) {

            throw new CommonException("用户名为空");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = this.getOne(queryWrapper);
        if (user == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        if (userId == null) {
            throw new CommonException("用户ID为空");
        }
        User user = this.getById(userId);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    /**
     * 更新用户点赞总数
     * @param flag
     * @param userId
     */
    @Override
    public void updateFavoriteCountById(Integer flag,Long userId) {
        User user = getById(userId);
        if (flag==1){
            user.setFavoriteCount(user.getFavoriteCount()+1);
        }else if(flag==-1){
            user.setFavoriteCount(user.getFavoriteCount()-1);
        }
        updateById(user);
    }

    /**
     * 更新用户获赞总数
     * @param flag
     * @param userId
     */
    @Override
    public void updateTotalFavoriteById(Integer flag,Long userId) {
        User user = getById(userId);
        if (flag==1){
            user.setTotalFavorite(user.getTotalFavorite()+1);
        }else if(flag==-1){
            user.setTotalFavorite(user.getTotalFavorite()-1);
        }
        updateById(user);
    }

    /**
     * 更新作品总数
     * @param flag
     * @param userId
     */
    @Override
    public void updateWorkCountById(Integer flag,Long userId) {
        User user = getById(userId);
        if (flag==1){
            user.setWorkCount(user.getWorkCount()+1);
        }else if(flag==-1){
            user.setWorkCount(user.getWorkCount()-1);
        }
        updateById(user);
    }

}
