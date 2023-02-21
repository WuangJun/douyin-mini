package com.douyin.manager;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.douyin.common.dto.UserLoginDTO;
import com.douyin.common.vo.FavoriteListResponseVO;
import com.douyin.common.vo.FavoriteResponseVO;
import com.douyin.common.vo.UserVO;
import com.douyin.common.vo.VideoVO;
import com.douyin.entity.Favorite;
import com.douyin.entity.Video;
import com.douyin.exception.CommonException;
import com.douyin.service.FavoriteService;
import com.douyin.service.UserService;
import com.douyin.service.VideoService;
import com.douyin.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * FavoriteManage
 *
 * @Author: jiahz
 * @Date: 2023/2/16 15:36
 * @Description:
 */
@Slf4j
@Service
public class FavoriteManage {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserManager userManager;

    /**
     * 登录用户对视频的点赞和取消点赞操作
     *
     * @param videoId
     * @param actionType
     * @return
     */
    @Transactional
    public FavoriteResponseVO action(Long userId, Long videoId, Integer actionType) {


        // 视频非空校验
        Video video = videoService.getById(videoId);
        if (video == null) {
            throw new CommonException("该视频不存在");
        }

        //不可以给自己点赞
        if(userId.equals(video.getAuthorId())){
            throw new CommonException("操作无效，不可以给自己点赞");
        }
        Favorite favoriteRecord = favoriteService.getByUserIdAndVideoId(userId, videoId);

        // 用户点赞
        if (actionType==1) {
            // 若favorite表中没有记录，则添加
            if (favoriteRecord == null) {
                Favorite favorite = new Favorite();
                favorite.setFavoriteUserId(userId);
                favorite.setVideoId(videoId);
                favoriteService.save(favorite);
            } else {
                // 若favorite表中有记录，且该记录已删除
                if (favoriteRecord.getIsDeleted() == 1) {
                    favoriteRecord.setIsDeleted(0);
                    favoriteService.updateById(favoriteRecord);
                } else {
                    // 若favorite表中有记录，且该记录未删除
                    throw new CommonException("请勿重复点赞");
                }
            }
            // 更新点赞总数
            videoService.updateFavoriteCountById(1, video.getId());
            log.info("更新视频点赞总个数成功！！！");
            userService.updateFavoriteCountById(1,userId);
            log.info("更新个人被点赞总个数成功！！！");
            userService.updateTotalFavoriteById(1,video.getAuthorId());
            log.info("更新个人喜爱作品个数成功！！！");
            // 用户取消点赞
        } else if (actionType==2) {
            // 若favorite表中有点赞记录
            if (favoriteRecord != null) {
                if (favoriteRecord.getIsDeleted() == 0) {
                    favoriteRecord.setIsDeleted(1);
                    favoriteService.updateById(favoriteRecord);
                } else {
                    throw new CommonException("该视频已经取消点赞，请勿重复操作");
                }
            } else {
                throw new CommonException("该视频还未点赞，无法取消");
            }
            // 更新视频点赞数
            videoService.updateFavoriteCountById(-1, video.getId());
            log.info("更新视频点赞总个数成功！！！");
            userService.updateFavoriteCountById(-1,userId);
            log.info("更新个人被点赞总个数成功！！！");
            userService.updateTotalFavoriteById(-1,video.getAuthorId());
            log.info("更新个人喜爱作品个数成功！！！");
        }
        return FavoriteResponseVO.success();
    }

    /**
     * 用户的所有点赞视频
     *
     * @param userId
     * @return
     */
    public FavoriteListResponseVO list(Long userId) {

        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("favorite_user_id", userId).eq("is_deleted", 0);
        List<Favorite> favoriteList = favoriteService.list(queryWrapper);

        if (favoriteList.isEmpty()) {
            log.info("当前没有视频信息！");
            return FavoriteListResponseVO.success(new ArrayList<>());
        }
        List<VideoVO> videoList = new ArrayList<>();

        for (Favorite favorite : favoriteList) {
            Long videoId = favorite.getVideoId();
            Video video = videoService.getById(videoId);
            if (video == null) {
                throw new CommonException("该视频已被删除");
            }
            VideoVO videoVO = new VideoVO();
            BeanUtils.copyProperties(video, videoVO);
            videoVO.set_favorite(true);
            UserVO userVO = userManager.getUserVOById(video.getAuthorId());
            videoVO.setAuthor(userVO);
            videoList.add(videoVO);
        }
        log.info("获取喜爱视频列表成功！！！！！！！！！！！！");

        return FavoriteListResponseVO.success(videoList);
    }
}
