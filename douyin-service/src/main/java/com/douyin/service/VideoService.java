package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface VideoService extends IService<Video> {

    List<Video> getPublishVideoList(Date latestTime);

    List<Video> getVideoListByUserId(Long userId);

    void updateFavoriteCountById(Integer flag,Long videoId);

    void updateCommentCountById(Integer flag,Long videoId);
}
