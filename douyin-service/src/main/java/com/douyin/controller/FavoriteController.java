package com.douyin.controller;

import com.douyin.aspect.ClientLogin;
import com.douyin.common.dto.UserLoginDTO;
import com.douyin.common.vo.FavoriteListResponseVO;
import com.douyin.common.vo.FavoriteResponseVO;
import com.douyin.common.vo.UserResponseVO;
import com.douyin.manager.FavoriteManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * FavoriteController
 *
 * @Author: jiahz
 * @Date: 2023/2/16 15:28
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    @Autowired
    private FavoriteManage favoriteManage;

    /**
     * 登录用户对视频的点赞和取消点赞操作
     *
     * @param token
     * @param videoId
     * @param actionType
     * @return
     */
    @ClientLogin
    @PostMapping("/action/")
    public FavoriteResponseVO favoriteAction(@RequestParam("token") String token,
                                         @RequestParam("video_id") Long videoId,
                                         @RequestParam("action_type") Integer actionType,
                                             UserLoginDTO userLoginDTO) {
        return favoriteManage.action(userLoginDTO.getId(), videoId, actionType);
    }

    /**
     * 用户的所有点赞视频
     *
     * @param token
     * @param userId
     * @return
     */
    @ClientLogin
    @GetMapping("/list/")
    public FavoriteListResponseVO favoriteList(@RequestParam("token") String token,
                                               @RequestParam("user_id") Long userId) {

        return favoriteManage.list(userId);
    }
}
