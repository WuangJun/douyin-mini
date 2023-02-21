package com.douyin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.douyin.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    public List<Comment> getByVideoId(Long videoId);
}
