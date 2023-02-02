package com.douyin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.douyin.entity.Video;
import com.douyin.mapper.VideoMapper;
import com.douyin.service.VideoService;
import org.springframework.stereotype.Service;

/**
 * Author:WJ
 * Date:2023/2/1 13:06
 * Description:<>
 */

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
}
