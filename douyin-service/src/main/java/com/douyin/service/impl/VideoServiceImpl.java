package com.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Video;
import com.douyin.exception.CommonException;
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

    /**
     * 根据时间获取最近三十条视频
     * @param latestTime
     * @return
     */
    @Override
    public List<Video> getPublishVideoList(Date latestTime) {
        try {
            LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.lt(Video::getCreatedTime,latestTime);
            queryWrapper.last("limit 30");
            queryWrapper.orderByDesc(Video::getCreatedTime);
            List<Video> videoList = list(queryWrapper);
            return videoList;
        }catch (Exception e){
            throw new CommonException("查询所有视频列表错误");
        }

    }

    /**
     * 更新视频点赞总数
     * @param flag
     * @param videoId
     */
    @Override
    public void updateFavoriteCountById(Integer flag,Long videoId) {
        Video video = getById(videoId);
        if (flag==1){
            video.setFavoriteCount(video.getFavoriteCount()+1);
        }else if(flag==-1){
            video.setFavoriteCount(video.getFavoriteCount()-1);
        }
        updateById(video);
    }


}
