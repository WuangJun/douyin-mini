package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

    @Select("SELECT * FROM dy_video_favorite WHERE favorite_user_id = #{userId} and video_id = #{videoId}")
    Favorite getByUserIdAndVideoId(@Param("userId") Long userId, @Param("videoId") String videoId);


}
