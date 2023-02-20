package com.douyin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VideoMapper extends BaseMapper<Video> {

    // @Select("select * from dy_video where unix_timestamp(create_time) < #{latestTime} order by create_time desc limit 30")
    // List<Video> getPublishVideoList(@Param("latestTime")Long latestTime);
}
