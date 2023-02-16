package com.douyin.service.impl;

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

    @Autowired
    private FavoriteMapper favoriteMapper;
    @Override
    public Favorite getByUserIdAndVideoId(Long userId, String videoId) {
        return favoriteMapper.getByUserIdAndVideoId(userId, videoId);
    }
}
