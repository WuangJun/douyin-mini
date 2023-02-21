package com.douyin.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.douyin.common.vo.VideoVO;
import com.douyin.entity.Favorite;
import com.douyin.entity.Video;
import com.douyin.exception.CommonException;
import com.douyin.service.FavoriteService;
import com.douyin.service.UserService;
import com.douyin.service.VideoService;
import com.douyin.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:WJ
 * Date:2023/2/18 14:09
 * Description:<>
 */
@Slf4j
@Service
public class VideoManager {

    @Autowired
    private UserService userService;
    @Autowired
    private VideoService videoService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private UserManager userManager;

    /**
     * 获取给定时间最近的30个视频
     *
     * @param latestTime
     * @param loginUserId
     * @return
     */
    public List<VideoVO> getVideoVOList(Long latestTime, Long loginUserId) {

        if (latestTime == null) {
            latestTime = DateUtils.curTimeMillis();
        }

        Date latestDate = null;
        try {
            latestDate = DateUtils.getLong2Date(latestTime);
        } catch (Exception e) {
            throw new CommonException("时间格式不对");
        }
        List<Video> videoList = videoService.getPublishVideoList(latestDate);
        if (videoList.isEmpty()) {
            log.info("当前没有视频信息！");
            return new ArrayList<>();
        }
        log.info("查询视频成功！");
        List<VideoVO> videoVos = new ArrayList<>(videoList.size());
        for (Video video : videoList) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(video,videoVO);
            // 没登录的人无法点赞
            if (loginUserId == null) {
                videoVO.set_favorite(false);
            } else {
                Favorite favorite = favoriteService.getByUserIdAndVideoId(loginUserId, video.getId());
                if (favorite == null) {
                    videoVO.set_favorite(false);
                } else {
                    videoVO.set_favorite(favorite.getIsDeleted() == 0);
                }
            }
            videoVO.setAuthor(userManager.getUserVOById(video.getAuthorId()));

            videoVos.add(videoVO);

        }
        return videoVos;
    }

    /**
     * 获取给定用户发表的所有视频
     *
     * @param userId
     * @param loginUserId
     * @return
     */
    public List<VideoVO> getVideoVOByUserId(Long userId, Long loginUserId) {

        List<Video> videoList = videoService.getVideoListByUserId(userId);
        if (videoList.isEmpty()) {
            log.info("当前没有视频信息！");
            return new ArrayList<>();
        }
        log.info("查询视频成功！");
        List<VideoVO> videoVos = new ArrayList<>(videoList.size());
        for (Video video : videoList) {
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(video,videoVO);

            Favorite favorite = favoriteService.getByUserIdAndVideoId(loginUserId, video.getId());
            if (favorite == null) {
                videoVO.set_favorite(false);
            } else {
                videoVO.set_favorite(favorite.getIsDeleted() == 0);
            }
            videoVO.setAuthor(userManager.getUserVOById(video.getAuthorId()));
            videoVos.add(videoVO);
        }
        return videoVos;
    }



}
