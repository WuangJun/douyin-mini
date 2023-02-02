package com.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Comment;
import com.douyin.mapper.CommentMapper;
import com.douyin.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * Author:WJ
 * Date:2023/2/1 13:12
 * Description:<>
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
}
