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

    /**
     * 登录用户对视频的点赞和取消点赞操作
     *
     * @param token
     * @param videoId
     * @param actionType
     * @return
     */
    @Transactional
    public FavoriteResponseVO action(String token, String videoId, String actionType) {

        // 用户鉴权
        if (StringUtils.isEmpty(token) || !TokenUtils.verify(token)) {
            throw new CommonException("用户未登陆");
        }
        UserLoginDTO loginUser = TokenUtils.getLoginUserDTO(token);
        if (loginUser == null) {
            throw new CommonException("用户未登陆");
        }

        // 视频非空校验
        Video video = videoService.getById(videoId);
        if (video == null) {
            throw new CommonException("该视频不存在");
        }

        Long userId = loginUser.getId();
        Favorite favoriteRecord = favoriteService.getByUserIdAndVideoId(userId, videoId);

        // 用户点赞
        if (actionType.equals("1")) {
            // 若favorite表中没有记录，则添加
            if (favoriteRecord == null) {
                Favorite favorite = new Favorite();
                favorite.setFavoriteUserId(userId);
                favorite.setVideoId(Long.valueOf(videoId));
                favoriteService.save(favorite);
            } else {
                // 若favorite表中有记录，且该记录已删除
                if (favoriteRecord.getIsDeleted() == 1) {
                    Favorite favorite = new Favorite();
                    favorite.setId(favoriteRecord.getId());
                    favorite.setIsDeleted(0);
                    favoriteService.updateById(favorite);
                } else {
                    // 若favorite表中有记录，且该记录未删除
                    throw new CommonException("请勿重复点赞");
                }
            }
        // 用户取消点赞
        } else if (actionType.equals("2")) {
            // 若favorite表中有点赞记录
            if (favoriteRecord != null) {
                if (favoriteRecord.getIsDeleted() == 0) {
                    Favorite favorite = new Favorite();
                    favorite.setId(favoriteRecord.getId());
                    favorite.setIsDeleted(1);
                    favoriteService.updateById(favorite);
                } else {
                    throw new CommonException("该视频已经取消点赞，请勿重复操作");
                }
            } else {
                throw new CommonException("该视频还未点赞，无法取消");
            }
        }
        return FavoriteResponseVO.success();
    }

    /**
     * 用户的所有点赞视频
     *
     * @param token
     * @param userId
     * @return
     */
    public FavoriteListResponseVO list(String token, String userId) {

        // 用户鉴权
        if (StringUtils.isEmpty(token) || !TokenUtils.verify(token)) {
            throw new CommonException("用户未登陆");
        }
        UserLoginDTO loginUser = TokenUtils.getLoginUserDTO(token);
        if (loginUser == null) {
            throw new CommonException("用户未登陆");
        }

        QueryWrapper<Favorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("favorite_user_id", userId).eq("is_deleted", 0);
        List<Favorite> favoriteList = favoriteService.list(queryWrapper);

        List<VideoVO> videoList = new ArrayList<>();

        for (Favorite favorite : favoriteList) {
            Long videoId = favorite.getVideoId();
            Video video = videoService.getById(videoId);
            VideoVO videoVO = VideoManager.videoConvertToVideoVO(video);

            videoVO.set_favorite(true);
            UserVO userVO = userService.getUserVOById(video.getAuthorId());
            videoVO.setUser(userVO);
            videoList.add(videoVO);
        }

        return FavoriteListResponseVO.success(videoList);
    }
}
