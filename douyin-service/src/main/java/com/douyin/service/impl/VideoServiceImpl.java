package com.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Video;
import com.douyin.mapper.VideoMapper;
import com.douyin.service.VideoService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:WJ
 * Date:2023/2/1 13:06
 * Description:<>
 */

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public List<Video> getPublishVideoList(Date latestTime) {
        // return videoMapper.getPublishVideoList(latestTime);
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.gt(Video::getCreatedTime,latestTime);
        queryWrapper.last("limit 30");
        queryWrapper.orderByDesc(Video::getCreatedTime);
        List<Video> videoList = list(queryWrapper);
        return videoList;
    }


}
