package com.douyin.controller;

import com.douyin.aspect.ClientLogin;
import com.douyin.common.dto.UserLoginDTO;
import com.douyin.common.vo.CommentListResponseVO;
import com.douyin.common.vo.CommentResponseVO;
import com.douyin.common.vo.FavoriteListResponseVO;
import com.douyin.manager.CommentManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * CommentController
 *
 * @author chengyu
 */
@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentManage commentManage;

    /**
     * 用户评论和删除评论
     *
     * @param token
     * @param videoId
     * @param actionType
     * @param commentText
     * @param commentId
     * @return
     */
    @ClientLogin
    @PostMapping("/action/")
    public CommentResponseVO commentResponseVO(@RequestParam("token") String token,
                                               @RequestParam("video_id") Long videoId,
                                               @RequestParam("action_type") Integer actionType,
                                               @RequestParam(value = "comment_text", required = false) String commentText,
                                               @RequestParam(value = "comment_id", required = false) Long commentId,
                                               UserLoginDTO userLoginDTO
    ) {
        return commentManage.action(userLoginDTO.getId(), videoId, actionType, commentText, commentId);
    }

    /**
     * 视频的所有评论列表
     *
     * @param token
     * @param videoId
     * @return
     */
    @ClientLogin
    @GetMapping("/list/")
    public CommentListResponseVO favoriteList(@RequestParam("token") String token,
                                              @RequestParam("video_id") Long videoId) {
        return commentManage.list(videoId);
    }

}
