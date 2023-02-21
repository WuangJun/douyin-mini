package com.douyin.manager;

import com.douyin.common.vo.*;
import com.douyin.entity.Comment;
import com.douyin.exception.CommonException;
import com.douyin.service.CommentService;
import com.douyin.service.FavoriteService;
import com.douyin.service.VideoService;
import com.douyin.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * CommentManage
 *
 * @author chengyu
 */


@Slf4j
@Service
public class CommentManage {


    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private CommentService commentService;

    /**
     * 仅登录用户对视频的评论和删除评论操作
     *
     * @param
     * @param videoId
     * @param actionType
     * @param videoId
     * @param actionType
     * @return
     */
    @Transactional
    public CommentResponseVO action(Long userId, Long videoId, Integer actionType, String commentText, Long commentId) {

        // 用户评论
        if (actionType == 1) {
            if (commentText == null) {
                throw new CommonException("评论信息为空!!");
            }
            Comment comment = new Comment();
            comment.setCommentText(commentText);
            comment.setCommentUserId(userId);
            comment.setVideoId(videoId);
            Date createTime = new Date();
            comment.setCreatedTime(createTime);
            boolean flag = commentService.save(comment);
            if (!flag) {
                throw new CommonException("保存评论失败");
            }
            log.info("保存评论成功");
            // 更新视频评论总数
            videoService.updateCommentCountById(1, videoId);
            log.info("更新视频评论总数成功");
            // 生成返回值commentVO
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            UserVO userVO = userManager.getUserVOById(userId);
            commentVO.setUser(userVO);
            // Date 类型转换成 MM-dd 字符串类型
            String creatDate = DateUtils.getDate2String(createTime);
            commentVO.setCreate_date(creatDate);
            return CommentResponseVO.success(commentVO);

            // 用户删除评论
        } else if (actionType == 2) {
            boolean flag = commentService.removeById(commentId);

            if (!flag) {
                throw new CommonException("未找到该条评论,删除失败！！！");
            }
            log.info("删除评论成功");
            // 更新视频评论总数
            videoService.updateCommentCountById(-1, videoId);
            log.info("更新视频评论总数成功");
            return CommentResponseVO.success();
        } else {
            throw new CommonException("输入指令不正确");
        }
    }

    /**
     * 获取视频评论列表
     *
     * @param videoId
     * @return
     */
    public CommentListResponseVO list(Long videoId) {

        List<Comment> commentList = commentService.getByVideoId(videoId);

        if (commentList.isEmpty()) {
            log.info("当前没有评论信息！");
            return CommentListResponseVO.success(new ArrayList<>());
        }
        // 借鸡生蛋：鸡-commentVO， 蛋-comment
        ArrayList<CommentVO> commentListVO = new ArrayList<>();

        for (Comment comment : commentList) {

            CommentVO commentVO = new CommentVO();

            BeanUtils.copyProperties(comment, commentVO);

            Long commentUserId = comment.getCommentUserId();
            UserVO userVO = userManager.getUserVOById(commentUserId);
            commentVO.setUser(userVO);
            // Date 类型转换成 MM-dd 字符串类型
            String creatDate = DateUtils.getDate2String(comment.getCreatedTime());
            commentVO.setCreate_date(creatDate);
            commentListVO.add(commentVO);
        }
        log.info("获取评论列表成功");
        return CommentListResponseVO.success(commentListVO);
    }

}


