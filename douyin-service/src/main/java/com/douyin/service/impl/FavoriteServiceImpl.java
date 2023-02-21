package com.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Favorite;
import com.douyin.mapper.FavoriteMapper;
import com.douyin.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:WJ
 * Date:2023/2/1 13:16
 * Description:<>
 */

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {


    /**
     * 通过用户Id和视频Id获取喜爱数据
     * @param userId
     * @param videoId
     * @return
     */
    @Override
    public Favorite getByUserIdAndVideoId(Long userId, Long videoId) {
        LambdaQueryWrapper<Favorite> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getVideoId,videoId).eq(Favorite::getFavoriteUserId,userId);
        return getOne(queryWrapper);
    }

    /**
     * 获取视频点赞数
     * @param videoId
     * @return
     */
    @Override
    public Integer getVideoFavoriteCountById(Long videoId) {
        LambdaQueryWrapper<Favorite> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getVideoId,videoId);
        Integer count = count(queryWrapper);
        return count;
    }
}
