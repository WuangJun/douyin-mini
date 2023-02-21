package com.douyin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Comment;
import com.douyin.entity.Video;
import com.douyin.mapper.CommentMapper;
import com.douyin.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:WJ
 * Date:2023/2/1 13:12
 * Description:<>
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    /**
     * 查找视频的所有评论
     * @param videoId
     * @return
     */
    @Override
    public List<Comment> getByVideoId(Long videoId) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getVideoId,videoId);
        queryWrapper.orderByDesc(Comment::getCreatedTime);
        return list(queryWrapper);
    }
}
