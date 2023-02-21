package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.common.dto.UserDTO;
import com.douyin.common.vo.UserVO;
import com.douyin.entity.User;

public interface UserService extends IService<User> {

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    public Long add(UserDTO userDTO);
    /**
     * 根据用户名查找
     * @param username
     * @return
     */
    public UserDTO getByUsername(String username);
    /**
     * 通过ID查找用户
     * @param userId
     * @return
     */
    public UserDTO getUserById(Long userId);

    public void updateFavoriteCountById(Integer flag,Long userId);

    public void updateTotalFavoriteById(Integer flag,Long userId);

    public void updateWorkCountById(Integer flag,Long userId);
}
