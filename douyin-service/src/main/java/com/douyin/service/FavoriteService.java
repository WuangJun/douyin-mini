package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.entity.Favorite;

public interface FavoriteService extends IService<Favorite> {

    Favorite getByUserIdAndVideoId(Long userId, String videoId);
}
